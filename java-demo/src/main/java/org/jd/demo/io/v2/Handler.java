package org.jd.demo.io.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Handler implements Runnable {
  static final int MAXIN = 1024 * 8;
  static final int MAXOUT = 1024 * 8;
  private final SocketChannel socketChannel;
  private final SelectionKey sk;

  private ByteBuffer readBuffer, writeBuffer;
  static ExecutorService pool = null;
  static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

  Handler(Selector selector, SocketChannel sc) throws IOException {
    socketChannel = sc;
    sc.configureBlocking(false);
    readBuffer = ByteBuffer.allocate(MAXIN);
    writeBuffer = ByteBuffer.allocate(MAXOUT);
    sk = socketChannel.register(selector, SelectionKey.OP_READ, this); // 注册接收Read事件，并将 Handler 作为callback对象

    pool = executor.getQueue().size() > 5 ? executor : null;
    System.out.println("New connection from " + sc.getRemoteAddress());
    selector.wakeup();
  }

  @Override
  public void run() {
    try {
      if (sk.isReadable()) {
        if (pool == null) {
          readRequest();
        } else {
          pool.execute(
                () -> {
                  try {
                    readRequest();// 业务逻辑处理
                  } catch (Exception e) {
                    e.printStackTrace();
                  }

                });
        }
      } else if (sk.isWritable()) {
        if (pool == null) {
          writeResponse();
        } else {
          pool.execute(
                () -> {
                  try {
                    writeResponse();// 响应结果写回客户端
                  } catch (Exception e) {
                    e.printStackTrace();
                  }

                });
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void readRequest() throws IOException {
    readBuffer.clear();
    int count = socketChannel.read(readBuffer);
    if (count > 0) {
      readBuffer.flip();
      while (readBuffer.hasRemaining()) {
        System.out.print((char) readBuffer.get());
      }
      socketChannel.register(sk.selector(), SelectionKey.OP_WRITE, this);
    } else {
      // 小于0时，客户端关闭input
      sk.cancel();
      socketChannel.close();
    }
  }

  private void writeResponse() throws IOException {
    writeBuffer.clear();
    writeBuffer.put("Message from server".getBytes());
    writeBuffer.flip();
    socketChannel.write(writeBuffer);
    socketChannel.register(sk.selector(), SelectionKey.OP_READ, this);
  }
}