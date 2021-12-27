package com.jz.demo.io.reactor;

import com.jz.demo.io.reactor.multi.MainDispacther;
import com.jz.demo.io.reactor.single.SingleDispacther;

import java.io.IOException;

public class ReactorMain {

    private static int port = 8888;

    public static void main(String[] args) throws Exception {
//        singleReactorRun();
        multiReactorRun();
    }

    private static void singleReactorRun() throws IOException {
        Thread dispacther = new Thread(new SingleDispacther(port, 16));
        dispacther.start();
        try {
            dispacther.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void multiReactorRun() throws IOException {
        AbstractDispacther dispacther = new MainDispacther(port, 8);
        dispacther.setName("MainDispacther");
        Thread thread = new Thread(dispacther);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
