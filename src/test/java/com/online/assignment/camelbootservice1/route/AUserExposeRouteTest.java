package com.online.assignment.camelbootservice1.route;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.apache.camel.builder.AdviceWithRouteBuilder;

import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import static com.online.assignment.camelbootservice1.constants.UserConstants.GET_USER_ROUTE_ID;
import static com.online.assignment.camelbootservice1.constants.UserConstants.ROUTE_END;

@RunWith(CamelSpringRunner.class)
@ContextConfiguration(classes = Application.class, loader = CamelSpringDelegatingTestContextLoader.class)
public class AUserExposeRouteTest extends AnAbstractRoute {

    private Map<String, Object> headers;

   /* @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        RouteDefinition route = modelCamelContext.getRouteDefinition(GET_USER_ROUTE_ID);
        route.get()adviceWith(modelCamelContext, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith(inputEndpoint);
                weaveById(ROUTE_END).after().to(resultEndpoint);
            }
        });

        resultEndpoint.reset();
        headers = new HashMap<>();
    }*/
}
