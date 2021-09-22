package com.online.assignment.camelbootservice1.exception;

import org.springframework.stereotype.Component;

import lombok.*;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationException extends Exception {

    private String errorCode;
    private String errorMsg;
}
