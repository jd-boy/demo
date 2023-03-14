package org.jd.demo;

import lombok.SneakyThrows;

public class Test {

    private static int count;

    private static Object aa = new Object();

    @SneakyThrows
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                while (print()) {
                    synchronized (aa) {
                        aa.notify();
                        aa.wait();
                    }
                }
                synchronized (aa) {
                    aa.notifyAll();
                }
            } catch (Exception e){

            }
        }, "A").start();


        new Thread(() -> {
            try {
                synchronized (aa) {
                    aa.wait();
                }
                while (print()) {
                    synchronized (aa) {
                        aa.notify();
                        aa.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    private static boolean print() {
        if (count >= 100) {
            return false;
        }
        System.out.printf("%s = %d\n", Thread.currentThread().getName(), ++count);
        return true;
    }

}