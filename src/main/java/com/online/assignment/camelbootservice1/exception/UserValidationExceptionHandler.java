package com.online.assignment.camelbootservice1.exception;

import org.apache.camel.Exchange;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import static org.apache.camel.Exchange.CONTENT_TYPE;

public class UserValidationExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidationExceptionHandler.class);

    public void handleValidationException(Exchange exchange) {
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if (cause instanceof ValidationException) {
            String errorMessage = ((ValidationException) cause).getErrorMsg();
            String errorCode = ((ValidationException) cause).getErrorCode();
            LOGGER.error("A validation exception occurred because : {}", errorMessage);
            setResponse(exchange, errorCode, errorMessage);
        }
    }

    private void setResponse(Exchange exchange, String errorCode, String errorMessage) {
        exchange.getIn().removeHeaders("*");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_BAD_REQUEST);
        exchange.getIn().setHeader(CONTENT_TYPE, APPLICATION_JSON);
        exchange.getIn().setBody(errorMessage);
        RestClientErrorResponse restClientErrorResponse = new RestClientErrorResponse();
        restClientErrorResponse.setCode(errorCode);
        restClientErrorResponse.setMessage(errorMessage);
        restClientErrorResponse.setErrorType(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).toString());
        exchange.getIn().setBody(restClientErrorResponse);
    }
}
