package com.online.assignment.camelbootservice1.validators;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.online.assignment.camelbootservice1.exception.ValidationException;
import com.online.assignment.camelbootservice1.models.User;

import static org.apache.commons.lang3.StringUtils.isBlank;

import static com.online.assignment.camelbootservice1.constants.UserConstants.*;

@Component("userValidators")
public class UserValidators {

    Logger logger = LoggerFactory.getLogger(UserValidators.class);

    public void validateUserRequest(final Exchange exchange) throws ValidationException {
        final User request = exchange.getIn().getBody(User.class);
        validateName(request.getName());
        validateAge(request.getAge());
    }

    private void validateName(final String userName) throws ValidationException {

        String[] userNames = {"Hello", "Hi"};
        containsABoundValue(userName, userNames, "userName invalid. Valid values are Hello, Hi");
    }

    private void validateAge(final Long age) throws ValidationException {

        if (age > 20) {
            throw new ValidationException("E103", "User Age is greater then 20, User Age should be less then 20");
        }
    }

    private static void containsABoundValue(final String userName, String[] userNames,
                                            final String errorType) throws ValidationException {

        if (!ArrayUtils.contains(userNames, userName)) {
            throw new ValidationException("E102", errorType);
        }
    }

    public void verifyUserId(Exchange exchange) throws ValidationException {

        String userID = exchange.getIn().getHeader(USER_ID, String.class);

        if (isBlank(userID)) {
            logger.warn("No such field - userID ");
            throw new ValidationException("E104", "No such field - userID");
        }
    }
}
