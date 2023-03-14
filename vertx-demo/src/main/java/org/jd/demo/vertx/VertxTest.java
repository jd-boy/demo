package org.jd.demo.vertx;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.SneakyThrows;

public class VertxTest {

  @SneakyThrows
  public static void main(String[] args) {
    Future.future(p -> {
      for (int i = 0; i < 50000_0000; i++) ;
      for (int i = 0; i < 50000_0000; i++) ;
      for (int i = 0; i < 50000_0000; i++) ;
      for (int i = 0; i < 50000_0000; i++) ;
      for (int i = 0; i < 50000_0000; i++) ;
      System.out.println("执行完毕");
      p.tryComplete();
    }).onSuccess(e -> System.out.println("第一个success"))
          .onSuccess(e -> System.out.println("第二个success"))
          .onComplete(event -> System.out.println("第一个complete"))
          .onComplete(event -> System.out.println("第二个complete"));

    Thread.sleep(10000);
  }

}
