Application is written in Java 8. Can be built by maven

mvn package

and run

mvn spring-boot:run
or
java -jar target/BrickShop3-0.1.0.jar

Technologies
Application uses Spring Boot and Spring REST services. H2 is used as in-memory database.

Use cases
Application launches one REST service. Application is for creating, updating and retrieving orders and available on endpoint http://localhost:8080/orders/. Rest endpoints consume HTTP requests and produce responses in JSON format.

Adding order
Customer is added when their first order is being created. Following curl command adds '100' for customer krzysztof. Order is sent in request body and krzysztof customer in URL path variable.
curl -H "Content-Type: application/json" -X POST -d '100' http://localhost:8080/orders/krzysztof

Displaying customer’s orders
Orders added by krzysztof customer can be requested by
curl http://localhost:8080/orders/krzysztof
Sample:
[{"orderReferenceId":"f5a05ca7-fe34-4699-ab41-b34166900b9c","bricksCount":300}]

Updating order
curl -H "Content-Type: application/json" -X PUT -d '200' http://localhost:8080/orders/ f5a05ca7-fe34-4699-ab41-b34166900b9d
