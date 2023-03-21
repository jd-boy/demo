package org.jd.demo.io.v2;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Reactor implements Runnable {
  private final Selector selector;
  private final ServerSocketChannel serverSocket;

  public Reactor(int port) throws IOException {
    selector = Selector.open();
    serverSocket = ServerSocketChannel.open();
    serverSocket.bind(new InetSocketAddress(port));
    serverSocket.configureBlocking(false);
    SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    sk.attach(new Acceptor());
  }
  @Override
  public void run() {
    try {
      while (!Thread.interrupted()) {
        int readyChannels = selector.select();
        if (readyChannels == 0) continue;
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
          SelectionKey key = iterator.next();
          dispatch(key);
          iterator.remove();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void dispatch(SelectionKey key) {
    Runnable r = (Runnable) (key.attachment());
    if (r != null)
      r.run();
  }

  class Acceptor implements Runnable { // inner
    @Override
    public void run() {
      SocketChannel sc;
      try {
        sc = serverSocket.accept(); // non-blocking accept()
        if(sc != null){
          new Handler(selector, sc);
        }
      } catch(IOException ex){
        ex.printStackTrace();
      }
    }
  }
}