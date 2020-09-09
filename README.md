# spring-Boot-crud-tests-example

An example of a Spring boot microservice implementing CRUD covered by various kinds of autotests.


By default, the microservice is configured to work with a real MySql database, 
you must either install and configure the MySql database before starting. 
Or change the base type to H2 in the application.properties settings. 
H2 settings can be taken from test.properties


Scripts for creating MySql database. 
Creation and filling of the required table.

```
create db bookdb;

use bookdb;

CREATE TABLE book (
       id int(11) NOT NULL AUTO_INCREMENT,
       author varchar(255) DEFAULT NULL,
       category varchar(255) DEFAULT NULL,
       name varchar(255) DEFAULT NULL,
       pages int(11) DEFAULT NULL,
       price int(11) DEFAULT NULL,
       publication varchar(255) DEFAULT NULL,
       PRIMARY KEY (id)
     );

INSERT INTO book (author, category, name, pages, price, publication) VALUES
('Charles Dickens', 'essays', 'Fletcher and Webster', 100, 550, 'FR Publication'),
('George Eliot', 'journalism', 'Victorian era', 500, 200, 'US Publication'),
('George Orwell', 'novels', 'Animal Farm', 700, 240, 'UK Publication');

```

When building a project, various kinds of tests will be performed.

```mvn clean package```


After building the project, you can go to the target folder:

```cd target```

Then run the microservice:

```java -jar ./spring-boot-crud-tests-example-0.0.1-SNAPSHOT.jar```

By default, the service will run on localhost:8080


Request parameters can be viewed in the browser, in the Swagger UI after starting the microservice.

```
http://localhost:8080/swagger-ui

```
