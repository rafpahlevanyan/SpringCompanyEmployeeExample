package com.example.springcompanyemployeeexample.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateUserRequest {
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Email
    private String userEmail;
    @NotEmpty
    private String password;


}