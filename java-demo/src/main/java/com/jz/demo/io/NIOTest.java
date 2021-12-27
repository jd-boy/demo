package com.jz.demo.io;

import com.jz.demo.io.reactor.Processor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NIOTest {

    private static Selector selector;

    public static void main(String[] args) throws IOException {
        selector = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(8888), 1024);
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NIO服务器启动");
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 处理连接时间，注册写事件
                if (selectionKey.isAcceptable()) {
                    acceptEvent(selectionKey);
                } else if (selectionKey.isWritable()) {
                    writeEvent(selectionKey);
                } else if (selectionKey.isReadable()) {
                    readEvent(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    private static void readEvent(SelectionKey selectionKey) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        int len = socketChannel.read(buffer);
        if (len > 0) {
            buffer.flip();
            String content = StandardCharsets.UTF_8.decode(buffer).toString();
            System.out.println("接收到：" + content);
            int ops = selectionKey.interestOps();
            // 添加对写事件监听
            selectionKey.interestOps(ops | SelectionKey.OP_WRITE);
            selectionKey.selector().wakeup();
        } else if (len < 0) {
//            System.out.println("关闭连接：" + socketChannel);
            selectionKey.cancel();
            socketChannel.close();
        }
    }

    /**
     * 处理写事件
     * @param selectionKey
     * @throws IOException
     */
    private static void writeEvent(SelectionKey selectionKey) throws IOException {
//        System.out.println("写事件");
        SocketChannel writeSocketChannel = (SocketChannel) selectionKey.channel();
        String resp = IOUtils.buildResp();
        IOUtils.doSomething();
        writeSocketChannel.write(ByteBuffer.wrap(resp.getBytes()));
        int ops = selectionKey.interestOps();
        selectionKey.interestOps(ops ^ SelectionKey.OP_WRITE);
    }

    private static void acceptEvent(SelectionKey selectionKey) throws IOException {
        System.out.println("accept 事件");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel acceptSocketChannel = serverSocketChannel.accept();
        System.out.println(acceptSocketChannel.getRemoteAddress());
        acceptSocketChannel.configureBlocking(false);
        acceptSocketChannel.register(selector, SelectionKey.OP_READ);
    }

}
