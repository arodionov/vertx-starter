package io.vertx.client;

import io.vertx.core.Vertx;

/**
 * Created by arodionov on 5/7/17.
 */
public class VertxRunner {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    MyRestAPIClient client = new MyRestAPIClient(vertx);

    vertx.setPeriodic(3000, id -> {
      client.getNamesWithCircuitBreaker(ar -> {
        if (ar.succeeded()) {
          System.out.println("Names: " + ar.result().encode());
        } else {
          System.out.println("Unable to retrieve the list of names: "
            + ar.cause().getMessage());
        }
      });
    });

  }

}
