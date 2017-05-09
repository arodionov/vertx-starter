package io.vertx.client;

/**
 * Created by arodionov on 5/6/17.
 */

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class MyRestAPIClient {

  private HttpClient client;
  private CircuitBreaker circuitBreaker;

  public MyRestAPIClient(Vertx vertx) {
    // Create the HTTP io.vertx.client and configure the host and post.
    client = vertx.createHttpClient(new HttpClientOptions()
      .setDefaultHost("localhost")
      .setDefaultPort(8080)
    );
    createCircuitBreaker(vertx);
  }

  private void createCircuitBreaker(Vertx vertx) {
    circuitBreaker = CircuitBreaker.create("my-circuit-breaker", vertx,
      // Configure it
      new CircuitBreakerOptions()
        .setTimeout(5000) // Operation timeout
        .setFallbackOnFailure(true) // Call the fallback on failure
        .setResetTimeout(10000)); // Switch to the half-open state every 10s
  }

  public MyRestAPIClient(ServiceDiscovery discovery) {
    HttpEndpoint.getClient(discovery,
      new JsonObject().put("name", "my-rest-api"),
      ar -> {
        if (ar.failed()) {
          // No service
          System.out.println("No matching services");
        } else {
          client = ar.result();
          System.out.println("Found services");
        }
      });
  }

  public void close() {
    // Don't forget to close the io.vertx.client when you are done.
    client.close();
  }

  public void getNames(Handler<AsyncResult<JsonArray>> handler) {
    // Emit a HTTP GET
    client.get("/names",
      response ->
        // Handler called when the response is received
        // We register a second handler to retrieve the body
        response.bodyHandler(body -> {
          // When the body is read, invoke the result handler
          handler.handle(Future.succeededFuture(body.toJsonArray()));
        }))
      // Set the timeout (time given in ms)
      .setTimeout(5000)
      .exceptionHandler(t -> {
        // If something bad happen, report the failure to the passed handler
        handler.handle(Future.failedFuture(t));
      })
      // Call end to send the request
      .end();
  }

  public void getNamesWithCircuitBreaker(Handler<AsyncResult<JsonArray>> handler) {
    circuitBreaker.executeWithFallback(
      operation -> {
        client.get("/names",
          response ->
            response.bodyHandler(body -> {
              operation.complete(body.toJsonArray());
            }))
          .exceptionHandler(operation::fail)
          .end();
      },
      failure ->
        // Return an empty json array on failures
        new JsonArray()
    ).setHandler(handler); // Just call the handler.
  }

  public void addName(String name, Handler<AsyncResult<Void>> handler) {
    // Emit a HTTP POST
    client.post("/names",
      response -> {
        // Check the status code and act accordingly
        if (response.statusCode() == 200) {
          handler.handle(Future.succeededFuture());
        } else {
          handler.handle(Future.failedFuture(response.statusMessage()));
        }
      })
      .exceptionHandler(t -> handler.handle(Future.failedFuture(t)))
      // Pass the name we want to add
      .end(name);
  }
}
