package org.jd.demo.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SocketClientMain {

    private static Selector selector;

    public static void main(String[] args) throws Exception {
        selector = Selector.open();
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);
        client.connect(new InetSocketAddress("127.0.0.1", 8888));
        // 确保连接上才能监听事件
        while (!client.finishConnect());
        SelectionKey clientKey = client.register(selector, 0);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                int n = 3;
                while (n > 0) {
                    System.out.println("开始发送");
                    clientKey.interestOps(SelectionKey.OP_WRITE);
                    clientKey.selector().wakeup();
                    n--;
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("开始----");
        while (true) {
            int num = selector.select();
            System.out.println("num:" + num);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isWritable()) {
                    String msg = "hello word ";
                    System.out.println("发送：" + msg);
                    client.write(StandardCharsets.UTF_8.encode(msg));
                    int ops = selectionKey.interestOps();
                    selectionKey.interestOps(ops ^ SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                    selectionKey.selector().wakeup();
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(8);
                    int size;
                    List<byte[]> dataBytes = new ArrayList<>();
                    while ((size = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        dataBytes.add(byteBuffer.array());
                        byteBuffer.clear();
                    }
                    int ops = selectionKey.interestOps();
                    selectionKey.interestOps(ops ^ SelectionKey.OP_READ);
                    byteBuffer.flip();
                    System.out.println("接收到：" + StandardCharsets.UTF_8.decode(byteBuffer));
                }
                iterator.remove();
            }
        }
    }

}
