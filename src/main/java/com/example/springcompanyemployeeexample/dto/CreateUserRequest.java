package com.example.springcompanyemployeeexample.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateUserRequest {
    private int id;
    @NotEmpty(message = "Name can`t be empty")
    private String name;
    @NotEmpty(message = "Surname can`t be empty")
    private String surname;
    @NotEmpty(message = "Please input valid email address")
    private String userEmail;
    @NotEmpty(message = "Password can`t be empty")
    private String password;


}