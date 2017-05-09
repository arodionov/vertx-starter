# vertx-starter
This project was created based on article ['Reactive Microservices with Eclipse Vert.x'](https://www.eclipse.org/community/eclipse_newsletter/2016/october/article4.php)

## Project structure

* *se.MyRestAPIVerticle* - contains HttpServer code
* *cl.VertxRunner* - starts Vertx Rest API client (MyRestAPIClient) from *main* method
* *cl.MyRestAPIClientVerticle* - starts Vertx Rest API client (MyRestAPIClient) as a Verticle
* *st.MainVerticle* - deploys server (MyRestAPIVerticl) and client (MyRestAPIClientVerticle) Verticles and publishes HttpEndpoint at internal Vertx ServiceDiscovery
* *cl.MyRestAPIClient* - contains code for Rest API client

Class *cl.MyRestAPIClient* has two constructors:
* *MyRestAPIClient(Vertx vertx)* - with CircuitBreaker
* *MyRestAPIClient(ServiceDiscovery discovery)* - with ServiceDiscovery

With *VertxRunner* you will have CircuitBreaker functionality, and ServiceDiscovery functionality with *MyRestAPIClientVerticle*
