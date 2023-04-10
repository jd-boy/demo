package org.jd.demo;

import java.util.concurrent.*;

public class SystemCurrentTimeTest {

  public static void main(String[] args) {
    int num = 1000_0000;
    System.out.print("单线程" + num + "次System.currentTimeMillis调用总耗时：    ");
    System.out.println(singleThreadTest(() -> {
      long l = System.currentTimeMillis();
    } , num));
    System.out.print("单线程" + num + "次CacheClock.currentTimeMillis调用总耗时：");
    System.out.println(singleThreadTest(() -> {
      long l = CacheClock.currentTimeMillis();
    }, num));
    System.out.print("并发" + num + "次System.currentTimeMillis调用总耗时：      ");
    System.out.println(concurrentTest(() -> {
      long l = System.currentTimeMillis();
    }, num));
    System.out.print("并发"+num+"次CacheClock.currentTimeMillis调用总耗时：  ");
    System.out.println(concurrentTest(() -> {
      long l = CacheClock.currentTimeMillis();
    }, num));
  }



  /**
   * 单线程测试
   * @return
   */
  private static long singleThreadTest(Runnable runnable, int num) {
    long sum = 0;
    for (int i = 0; i < num; i++) {
      long begin = System.nanoTime();
      runnable.run();
      long end = System.nanoTime();
      sum += end - begin;
    }
    return sum;
  }

  /**
   * 并发测试
   * @return
   */
  private static long concurrentTest(Runnable runnable, int num) {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200,200,60,
          TimeUnit.SECONDS, new LinkedBlockingQueue<>(num));
    long[] sum = new long[] {0};
    //闭锁基于CAS实现，并不适合当前的计算密集型场景，可能导致等待时间较长
    CountDownLatch countDownLatch = new CountDownLatch(num);
    for (int i = 0; i < num; i++) {
      threadPoolExecutor.submit(() -> {
        long begin = System.nanoTime();
        runnable.run();
        long end = System.nanoTime();
        //计算复杂型场景更适合使用悲观锁
        synchronized(SystemCurrentTimeTest.class) {
          sum[0] += end - begin;
        }
        countDownLatch.countDown();
      });
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return sum[0];
  }


  /**
   * 缓存时钟，缓存System.currentTimeMillis()的值，每隔20ms更新一次
   */
  public static class CacheClock {
    //定时任务调度线程池
    private static ScheduledExecutorService timer = new ScheduledThreadPoolExecutor(1);
    //毫秒缓存
    private static volatile long timeMilis;

    static {
      //每秒更新毫秒缓存
      timer.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
          timeMilis = System.currentTimeMillis();
        }
      },0,1000,TimeUnit.MILLISECONDS);
    }

    public static long currentTimeMillis() {
      return timeMilis;
    }
  }

}