package com.online.assignment.camelbootservice1.validators;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.online.assignment.camelbootservice1.exception.ValidationException;
import com.online.assignment.camelbootservice1.models.User;

@Component("userValidators")
public class UserValidators {

    public void validateUserRequest(final Exchange exchange) throws ValidationException {
        final User request = exchange.getIn().getBody(User.class);
        validateNameAndDataType(request.getName());
        validateAge(request.getAge());
        validateDOB(request.getDob());
    }


    private void validateNameAndDataType(final String name) throws ValidationException {

        if (!StringUtils.isAlphaSpace(name)) {
            throw new ValidationException("E105", "User name should contains alpha chars or space");
        }
        String[] userNames = {"Hello", "Hi"};
        containsABoundValue(name, userNames, "userName invalid. Valid values are Hello & Hi");
    }

    private void validateAge(final Long age) throws ValidationException {

        if (age > 20) {
            throw new ValidationException("E103", "User Age is greater then 20, User Age should be less then 20");
        }
    }

    private void validateDOB(String dob) throws ValidationException {
        String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
        boolean result = dob.matches(regex);
        if (!result) {
            throw new ValidationException("E106", "Invalid dob, not in the correct format : dd-mm-yyyy");
        }
    }

    private static void containsABoundValue(final String userName, String[] userNames,
                                            final String errorType) throws ValidationException {

        if (!ArrayUtils.contains(userNames, userName)) {
            throw new ValidationException("E102", errorType);
        }
    }
}
