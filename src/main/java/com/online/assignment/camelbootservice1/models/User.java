package com.online.assignment.camelbootservice1.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor
@ToString
public class User {

    private int userId;
    private String name;
    private Long age;
    private String dob;
    private Double salary;
}
