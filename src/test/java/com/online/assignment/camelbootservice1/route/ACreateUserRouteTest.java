package com.online.assignment.camelbootservice1.route;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.apache.camel.test.spring.junit5.CamelSpringTestContextLoader;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.online.assignment.camelbootservice1.CamelBootService1Application;
import com.online.assignment.camelbootservice1.exception.RestClientErrorResponse;
import com.online.assignment.camelbootservice1.models.User;

import static org.apache.camel.builder.AdviceWith.adviceWith;
import static org.apache.camel.component.mock.MockEndpoint.assertIsSatisfied;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static com.online.assignment.camelbootservice1.constants.UserConstants.CREATE_USER_ROUTE_ID;
import static com.online.assignment.camelbootservice1.constants.UserConstants.FILE_TYPE;
import static com.online.assignment.camelbootservice1.constants.UserConstants.ROUTE_END;

@CamelSpringTest
@ContextConfiguration(loader = CamelSpringTestContextLoader.class, classes = CamelBootService1Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ACreateUserRouteTest extends AnAbstractRoute {

    @Autowired
    private ModelCamelContext camelContext;

    @EndpointInject("mock:result")
    private MockEndpoint mockResult;

    @Produce("direct:createUserDetails")
    private ProducerTemplate start;

    @BeforeEach
    public void setup() throws Exception {
        adviceWith(camelContext, CREATE_USER_ROUTE_ID, route -> route.weaveById(ROUTE_END).after().to(mockResult));
        mockResult.reset();
    }

    @Test
    public void whenUserDataHavingInvalidName() throws Exception {
        // Given

        final User request = buildUserDataRequest();
        //setting invalid name
        request.setName("Hello4");

        mockResult.expectedMessageCount(1);

        //When
        start.sendBodyAndHeader(request, FILE_TYPE, "CSV");

        //Then
        assertIsSatisfied(mockResult);
        final Exchange exchange = mockResult.getExchanges().get(0);
        Assert.assertNotNull(exchange);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));

        final String responseBody = exchange.getIn().getBody(String.class);
        assertNotNull(responseBody);

        final RestClientErrorResponse actualResponse =unmarshal(responseBody, RestClientErrorResponse.class);
        assertNotNull(actualResponse);

        assertNotNull(actualResponse.getCode());
        assertEquals("E105", actualResponse.getCode());
        assertEquals("User name should contains alpha chars or space", actualResponse.getMessage());

    }

    @Test
    public void whenUserDataHavingInvalidAge() throws Exception {
        // Given

        final User request = buildUserDataRequest();
        //setting invalid age
        request.setAge(25L);

        mockResult.expectedMessageCount(1);

        //When
        start.sendBodyAndHeader(request, FILE_TYPE, "CSV");

        //Then
        assertIsSatisfied(mockResult);
        final Exchange exchange = mockResult.getExchanges().get(0);
        Assert.assertNotNull(exchange);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));

        final String responseBody = exchange.getIn().getBody(String.class);
        assertNotNull(responseBody);

        final RestClientErrorResponse actualResponse =unmarshal(responseBody, RestClientErrorResponse.class);
        assertNotNull(actualResponse);

        assertNotNull(actualResponse.getCode());
        assertEquals("E103", actualResponse.getCode());
        assertEquals("User Age is greater then 20, User Age should be less then 20", actualResponse.getMessage());

    }

    @Test
    public void whenUserDataHavingInvalidDob() throws Exception {
        // Given

        final User request = buildUserDataRequest();
        //setting invalid DOB
        request.setDob("13-13-2021");

        mockResult.expectedMessageCount(1);

        //When
        start.sendBodyAndHeader(request, FILE_TYPE, "CSV");

        //Then
        assertIsSatisfied(mockResult);
        final Exchange exchange = mockResult.getExchanges().get(0);
        Assert.assertNotNull(exchange);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));

        final String responseBody = exchange.getIn().getBody(String.class);
        assertNotNull(responseBody);

        final RestClientErrorResponse actualResponse =unmarshal(responseBody, RestClientErrorResponse.class);
        assertNotNull(actualResponse);

        assertNotNull(actualResponse.getCode());
        assertEquals("E106", actualResponse.getCode());
        assertEquals("Invalid dob, not in the correct format : dd-mm-yyyy", actualResponse.getMessage());

    }

    @Test
    public void whenUserDataHavingUnauthorisedName () throws Exception {
        // Given

        final User request = buildUserDataRequest();
        //setting Unauthorised name
        request.setName("Hey");

        mockResult.expectedMessageCount(1);

        //When
        start.sendBodyAndHeader(request, FILE_TYPE, "CSV");

        //Then
        assertIsSatisfied(mockResult);
        final Exchange exchange = mockResult.getExchanges().get(0);
        Assert.assertNotNull(exchange);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));

        final String responseBody = exchange.getIn().getBody(String.class);
        assertNotNull(responseBody);

        final RestClientErrorResponse actualResponse =unmarshal(responseBody, RestClientErrorResponse.class);
        assertNotNull(actualResponse);

        assertNotNull(actualResponse.getCode());
        assertEquals("E102", actualResponse.getCode());
        assertEquals("userName invalid. Valid values are Hello & Hi", actualResponse.getMessage());

    }

    @Test
    public void whenUserDataCreatedSuccessfully () throws Exception {
        // Given

        final User request = buildUserDataRequest();

        mockResult.expectedMessageCount(1);

        //When
        start.sendBodyAndHeader(request, FILE_TYPE, "CSV");

        //Then
        assertIsSatisfied(mockResult);
        final Exchange exchange = mockResult.getExchanges().get(0);
        Assert.assertNotNull(exchange);

        final User responseBody = exchange.getIn().getBody(User.class);
        assertNotNull(responseBody);


        assertEquals("Hello", responseBody.getName());
        assertEquals("20-08-2020", responseBody.getDob());

    }
}
