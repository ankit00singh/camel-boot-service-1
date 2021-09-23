package com.online.assignment.camelbootservice1.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.online.assignment.camelbootservice1.models.User;

import static com.online.assignment.camelbootservice1.constants.UserConstants.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.apache.camel.model.rest.RestParamType.path;

@Component("userExposeRoute")
public class UserExposeRoute extends AbstractUserRoute {

    @Value("${rest.api.base.url:/com/user/service1/v1}")
    private String restApiBaseUrl;

    @Value("${rest.api.version:1.0}")
    private String restApiVersion;

    @Value("${rest.api.title}")
    private String restApiTitle;

    @Value("${rest.api.description}")
    private String restApiDesc;

    @Value("${swagger.url:/apidocs}")
    private String swaggerUrl;

    @Value("${rest.api.host}")
    private String restApiHost;

    @Value("${user.get.resource.path:/getUser}")
    private String getUserResourcePath;

    @Value("${user.create.resource.path:/createUser}")
    private String createUserResourcePath;

    @Value("${user.update.resource.path:/updateUser}")
    private String updateUserResourcePath;

    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();

        initializeExceptionHandlers();

            restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES")
                .host(restApiHost)
                .port(9095)
                .contextPath(restApiBaseUrl)
                .apiContextPath(swaggerUrl)
                .apiProperty("api.description", restApiDesc)
                .apiProperty("api.title", restApiTitle)
                .apiProperty("api.version", restApiVersion)
                .apiProperty("cors", "true")
                .setSkipBindingOnErrorCode(true);

        // Get/Create/Update User Info from file
        rest().description("Get User Info REST Service")
            .consumes(APPLICATION_JSON).produces(APPLICATION_JSON)

            .get(getUserResourcePath)
            .produces(APPLICATION_JSON)
            .responseMessage().code(200).message("The Get User model").responseModel(User.class).endResponseMessage()
            .description("Get User details from file")
            .outType(User.class)
            .to("direct:getUserDetails")

            .post(createUserResourcePath + "?" +  FILE_TYPE + "={" + FILE_TYPE + "}")
            .consumes(APPLICATION_JSON).produces(APPLICATION_JSON)
            .param().name(FILE_TYPE).type(path).description("Input File Type").dataType("string").endParam()
            .responseMessage().code(200).message("Create User Response model").responseModel(User.class).endResponseMessage()
            .description("Create User details to file")
            .type(User.class)
            .outType(User.class)
            .to("direct:createUserDetails")

            .put(updateUserResourcePath + "?" +  FILE_TYPE + "={" + FILE_TYPE + "}")
            .consumes(APPLICATION_JSON).produces(APPLICATION_JSON)
            .param().name(FILE_TYPE).type(path).description("Input File Type").dataType("string").endParam()
            .responseMessage().code(200).message("Update User Response model").responseModel(User.class).endResponseMessage()
            .description("Update User details to file")
            .outType(User.class)
            .to("direct:updateUserDetails");

        from("direct:getUserDetails")
            .routeId(GET_USER_ROUTE_ID)
            .onCompletion()
                .log(LoggingLevel.DEBUG, "Get User Details request execution completed.")
                .id(ROUTE_END)
            .end()
            .log(LoggingLevel.INFO, "Get User Details request received ")
            .to("rest:get:/com/user/service2/v1/getUserDetails?bridgeEndpoint=true")
            .unmarshal().json(JsonLibrary.Jackson, User.class)
            .removeHeaders("*", HTTP_RESPONSE_CODE, CONTENT_TYPE);

        from("direct:createUserDetails")
            .routeId(CREATE_USER_ROUTE_ID)
            .onCompletion()
                .log(LoggingLevel.DEBUG, "Create User Details request execution completed.")
                .id(ROUTE_END)
            .end()
            .log(LoggingLevel.INFO, "Create User Details request received for File Type : ${headers." + FILE_TYPE + "}")
            .bean("userValidators", "validateUserRequest")
            .removeHeaders("*", HTTP_RESPONSE_CODE, CONTENT_TYPE);

        from("direct:updateUserDetails")
            .routeId(UPDATE_USER_ROUTE_ID)
            .onCompletion()
                .log(LoggingLevel.DEBUG, "Update User Details request execution completed.")
                .id(ROUTE_END)
            .end()
            .log(LoggingLevel.INFO, "Update User Details request received for File Type : ${headers." + FILE_TYPE + "}")
            .bean("userValidators", "validateUserRequest")
            .removeHeaders("*", HTTP_RESPONSE_CODE, CONTENT_TYPE);

        }
}
