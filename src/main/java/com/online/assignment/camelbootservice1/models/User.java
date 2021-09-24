package com.online.assignment.camelbootservice1.models;

import lombok.*;
import java.io.*;

@Getter @Setter @NoArgsConstructor
@ToString
public class User implements Serializable{

    private int userId;
    private String name;
    private Long age;
    private String dob;
    private Double salary;
}
