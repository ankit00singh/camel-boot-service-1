package com.online.assignment.camelbootservice1.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.online.assignment.camelbootservice1.models.User;

/**
 * Abstract Route.
 *
 * @author Ankit Kumar
 */
public abstract class AnAbstractRoute {

    public static User buildUserDataRequest() throws Exception {
        User user = new User();
        user.setUserId(2);
        user.setName("Hello");
        user.setDob("20-08-2020");
        user.setSalary(122111241.150);
        user.setAge(20L);
        return user;
    }

}
