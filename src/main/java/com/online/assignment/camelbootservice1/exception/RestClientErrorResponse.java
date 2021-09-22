package com.online.assignment.camelbootservice1.exception;

import lombok.*;

@Getter @Setter @NoArgsConstructor
@ToString
public class RestClientErrorResponse {

    private String errorType;
    private String code;
    private String message;
}
