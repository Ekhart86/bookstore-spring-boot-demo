# spring-Boot-crud-tests-example

Sample Spring Boot Microservice Implementing CRUD 
with Spring Security Basic Authentication, covered unit and integration tests.


By default, the microservice is configured to work with a real MySql database, 
you must either install and configure the MySql database before starting. 

You must specify the correct database settings in application.properties

```
spring.datasource.url=url
spring.datasource.username=name
spring.datasource.password=password
```


It will be enough for a database to be created in the database, the tables will be created automatically.
Command below tells Hidernate to create or modify tables in the database to match the entities in the code.
```
spring.jpa.hibernate.ddl-auto = update
```

Or change the base type to H2 in the application.properties settings. 
H2 settings can be taken from test.properties

Scripts for creating a MySql database. 

```
create db bookdb;

use bookdb;

CREATE TABLE book (
  id int(11) NOT NULL AUTO_INCREMENT,
  author varchar(255) NOT NULL,
  category varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  pages int(11) NOT NULL,
  price int(11) NOT NULL,
  publication varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO book (author, category, name, pages, price, publication) VALUES
('Charles Dickens', 'essays', 'Fletcher and Webster', 100, 550, 'FR Publication'),
('George Eliot', 'journalism', 'Victorian era', 500, 200, 'US Publication'),
('George Orwell', 'novels', 'Animal Farm', 700, 240, 'UK Publication');

CREATE TABLE users (
       id int(11) NOT NULL AUTO_INCREMENT,
       username varchar(255) NOT NULL UNIQUE,
       password varchar(255) NOT NULL,
       PRIMARY KEY (id)
);

```

When building a project, various kinds of tests will be performed.

```mvn clean package```


After building the project, you can go to the target folder:

```cd target```

Then run the microservice:

```java -jar ./spring-boot-crud-tests-example-0.0.1-SNAPSHOT.jar```

By default, the service will run on localhost:8080


Request parameters can be viewed in the browser, in the Swagger UI after starting the microservice.
Also, there you can execute all requests to the service.
http://localhost:8080/swagger-ui.html.
The admin user is saved to the database when the application starts, if it is not there yet.


To send requests you need basic auth:

If use UI Swagger:
```
login : admin
password: password
```

If you send a request using postman then add a header:
```
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```
User passwords are stored in the users table encoded using the BCrypt Encoder.

![Auth -1](https://user-images.githubusercontent.com/25115868/92894974-c0914500-f423-11ea-9ab5-10bd30804bd7.PNG)

![Auth-2](https://user-images.githubusercontent.com/25115868/92895087-dd2d7d00-f423-11ea-9545-39b82042ec56.PNG)
