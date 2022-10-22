package org.jd.demo.io.reactor;

import org.jd.demo.io.reactor.multi.MainDispacther;
import org.jd.demo.io.reactor.single.SingleDispacther;

import java.io.IOException;

public class ReactorMain {

    private static int port = 8888;

    public static void main(String[] args) throws Exception {
//        singleReactorRun(-1);
        multiReactorRun(4);
    }

    private static void singleReactorRun(int processorNum) throws IOException {
        Thread dispacther = new Thread(new SingleDispacther(port, processorNum));
        dispacther.start();
        try {
            dispacther.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void multiReactorRun(int subDispactherNum) throws IOException {
        AbstractDispacther dispacther = new MainDispacther(port, subDispactherNum);
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
