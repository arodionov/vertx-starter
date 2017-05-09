# vertx-starter
This project was created based on article ['Reactive Microservices with Eclipse Vert.x'](https://www.eclipse.org/community/eclipse_newsletter/2016/october/article4.php)

## Project structure

*server*
* *MyRestAPIVerticle* - Vertx HttpServer with Rest API

*client*
* *VertxRunner* - starts Vertx Rest API client (MyRestAPIClient) from *main* method
* *MyRestAPIClientVerticle* - starts Vertx Rest API client (MyRestAPIClient) as a Verticle
* *MyRestAPIClient* - contains code for Rest API client

*starter*
* *MainVerticle* - deploys server (MyRestAPIVerticl) and client (MyRestAPIClientVerticle) Verticles and publishes HttpEndpoint at internal Vertx ServiceDiscovery


Class *client.MyRestAPIClient* has two constructors:
* *MyRestAPIClient(Vertx vertx)* - with CircuitBreaker
* *MyRestAPIClient(ServiceDiscovery discovery)* - with ServiceDiscovery

You have CircuitBreaker functionality with *VertxRunner*, and ServiceDiscovery functionality with *MyRestAPIClientVerticle*
