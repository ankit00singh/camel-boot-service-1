package com.online.assignment.camelbootservice1.routes;

import com.online.assignment.camelbootservice1.exception.UserValidationExceptionHandler;
import com.online.assignment.camelbootservice1.exception.ValidationException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractUserRoute extends RouteBuilder {


    protected void initializeExceptionHandlers() throws Exception {

        onException(ValidationException.class)
            .log(LoggingLevel.ERROR, "A Validation Error occurred - ${exception.stacktrace}")
            .handled(true)
            .bean(UserValidationExceptionHandler.class, "handleValidationException").marshal().json();

    }
}
