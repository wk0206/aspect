# aspect
java -jar aspect-0.0.1-SNAPSHOT.jar

Entrance: http://localhost:8080/index.html

after start, already 2 element inside. 
clear the queue if you want.

detailed endpoint:
      
      type : "POST",
      url : "http://localhost:8080/orders",

      type : "GET",
      data : "",
      url : "http://localhost:8080/order/" + id,


      type : "POST",
      data : "quantity=" + quantity,
      url : "http://localhost:8080/order/" + id,

      type : "DELETE",
      data : "",
      url : "http://localhost:8080/order/" + id,


      type : "POST",
      data : "",
      url : "http://localhost:8080/nextDelivery",

      type : "GET",
      data : "",
      url : "http://localhost:8080/clearQueue",
