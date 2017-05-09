/**
 * Created by arodionov on 5/6/17.
 */

package io.vertx.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.servicediscovery.ServiceDiscovery;

public class MyRestAPIClientVerticle extends AbstractVerticle {

  @Override
  public void start() {

    ServiceDiscovery serviceDiscovery = ServiceDiscovery.create(vertx);
    MyRestAPIClient client = new MyRestAPIClient(serviceDiscovery);

    vertx.setPeriodic(3000, id -> {
      client.getNames(ar -> {
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
