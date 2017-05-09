# vertx-starter
This project was created based on article ['Reactive Microservices with Eclipse Vert.x'](https://www.eclipse.org/community/eclipse_newsletter/2016/october/article4.php)

## Project structure

* *MyRestAPIVerticle* - contains HttpServer code
* *VertxRunner* - starts Vertx Rest API client (MyRestAPIClient) from *main* method
* *MyRestAPIClientVerticle* - starts Vertx Rest API client (MyRestAPIClient) as a Verticle
* *MainVerticle* - starts server (MyRestAPIVerticl) and client (MyRestAPIClientVerticle) Verticles and publish HttpEndpoint at Vertx ServiceDiscovery
* *MyRestAPIClient* - contains code for Rest API client
