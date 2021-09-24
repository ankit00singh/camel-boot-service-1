package com.online.assignment.camelbootservice1.route;

import org.apache.camel.*;
import org.apache.camel.builder.AdviceWith;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.apache.camel.test.spring.junit5.CamelSpringTestContextLoader;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.online.assignment.camelbootservice1.CamelBootService1Application;
import com.online.assignment.camelbootservice1.models.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static com.online.assignment.camelbootservice1.constants.UserConstants.*;

@CamelSpringTest
@ContextConfiguration(loader = CamelSpringTestContextLoader.class, classes = CamelBootService1Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AUserExposeRouteTest extends AnAbstractRoute {

    @Autowired
    private ModelCamelContext camelContext;

    @EndpointInject("mock:result")
    private MockEndpoint mockResult;

    @Produce("direct:createUserDetails")
    private ProducerTemplate start;

    @BeforeEach
    public void setup() throws Exception {
        AdviceWith.adviceWith(camelContext, CREATE_USER_ROUTE_ID, route -> route.weaveAddLast().to(mockResult));
        mockResult.reset();
    }

    @Test
    public void whenUserNameIsInvalid() throws Exception {

        //Given
        final User request = buildUserDataRequest();
        //setting invalid name
        request.setName("Hello4");

        mockResult.expectedMessageCount(1);

        //When
        start.sendBodyAndHeader(request, FILE_TYPE, "CSV");

        //Then
        final Exchange exchange = mockResult.getReceivedExchanges().get(0);
        assertNotNull(exchange);
        assertEquals(HttpStatus.SC_BAD_REQUEST, exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
    }
}
