package com.jz.demo.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOTest {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888, 1024, null);
        System.out.println("服务启动");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket.getRemoteSocketAddress());
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.append(IOUtils.buildResp());
            bufferedWriter.flush();
        }
    }

}
