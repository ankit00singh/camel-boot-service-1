package com.online.assignment.camelbootservice1.route;

import java.io.IOException;

import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.assignment.camelbootservice1.models.User;

/**
 * Abstract Route.
 *
 * @author Ankit Kumar
 */
@TestPropertySource(locations = {"file:src/main/resources/application.properties"})
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
    public static <T> T unmarshal(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

}
