package io.vertx.starter;

import io.vertx.client.MyRestAPIClientVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.server.MyRestAPIVerticle;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();

    ServiceDiscovery discovery = ServiceDiscovery.create(vertx);
    discovery.publish(HttpEndpoint.createRecord(
      "my-rest-api",
      "localhost", 8080,
      "/names"),
      ar -> {
        if (ar.succeeded()) {
          System.out.println("REST API published");
          Record record = ar.result();
        } else {
          System.out.println("Unable to publish the REST API: " +
            ar.cause().getMessage());
        }
      });

    vertx.deployVerticle(MyRestAPIVerticle.class.getName());
    vertx.deployVerticle(MyRestAPIClientVerticle.class.getName());

  }

}
