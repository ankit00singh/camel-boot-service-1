# camel-boot-service-1

This application consists of validation of request and gRPC call of internal service ( camel-boot-service-2).

## Tech Stack

    * Java 8
    * Spring Boot 2.4.9
    * gRPC
    * Apache Camel 3.10.0
    * Lombok for removing boilerplate code
    * JSON.jackson for marshalling & unmarshalling
    * Rest Assured for unit tests

You can add more dependencies e.g. Spring Data JPA, Spring MongoDB, Spring Redis etc. by going to the
[Spring Initializer website](https://start.spring.io)

## Camel

This application uses Apache Camel as an integration layer. Some Camel <-> Spring Boot configuration is defined in
the [config file here](resource/application.properties)

**_Note_**, for using any camel components, always try to use the spring boot starter version of the component.

_E.g. `camel-servlet` for use with Spring Boot is `camel-servlet-starter`_

This is because using started components auto-configure at startup for most scenarios, which leaves us to use less camel
specific custom configuration.

## Build

This application uses a maven pom.xml for builds. To build run:

On Windows:

    mvn clean build

## validator package

Having request validation at data and data type level.

Unlike older Camel/Spring Boot applications, integration tests are consolidated back into the `src/test` package

The package structure should look like this:

    src
    |- test
        |- java
            |- com.online.assignment
                |- route                        # Camel Unit tests - route level unit test


## API Docs

Swagger API documentation is exposed at [Swagger URL](http://localhost:9093/com/user/service1/v1/apidocs)

## Actuator

Spring Actuator endpoints are available to view at [Actuator URL](http://localhost:9093/actuator/)

All actuator endpoints are exposed, you can configure them via the property `management.endpoints.web.exposure.include`
in the `application.properties` [config file](config/application.properties)

## TODO : Implementation of proto buffer google format