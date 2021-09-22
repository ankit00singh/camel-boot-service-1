package com.online.assignment.camelbootservice1.routes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.online.assignment.camelbootservice1.exception.RestClientErrorResponse;
import com.online.assignment.camelbootservice1.exception.UserValidationExceptionHandler;
import com.online.assignment.camelbootservice1.exception.ValidationException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public abstract class AbstractUserRoute extends RouteBuilder {


    protected void initializeExceptionHandlers() throws Exception {

        onException(ValidationException.class)
            .log(LoggingLevel.ERROR, "A Validation Error occurred - ${exception.stacktrace}")
            .handled(true)
            .bean(UserValidationExceptionHandler.class, "handleValidationException").marshal().json();

    }
}
