package com.jz.demo.vertx;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class TestHandler implements Handler<RoutingContext> {

    private final WorkerExecutor workerExecutor;


    public TestHandler(WorkerExecutor workerExecutor) {
        this.workerExecutor = workerExecutor;
    }

    @Override
    public void handle(RoutingContext context) {
        JsonObject json = context.getBodyAsJson();
//        MultiMap queryParams = context.queryParams();
//        queryParams.forEach(System.out::println);
//        System.out.println(Thread.currentThread().getName());
//        CompositeFuture.all(workerExecutor.executeBlocking(a -> {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            System.out.println(Thread.currentThread().getName() + "  1处理完毕");
//            a.complete();
//        }, false), workerExecutor.executeBlocking(a -> {
//            try {
//                Thread.sleep(4);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            System.out.println(Thread.currentThread().getName() + "  2处理完毕");
//            a.complete();
//        }, false)).onComplete(c -> {
//            System.out.println("处理完毕");
//            if (!context.response().ended()) {
//                context.response().end("结束", "utf-8");
//            }
//        });
//        System.out.println(Thread.currentThread().getName() + "  流程完毕");
        try {
            if (true) {
//                throw new RuntimeException("kfk");
            }
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!context.response().ended()) {
            context.response().end("结束", "utf-8");
        }
    }

}
