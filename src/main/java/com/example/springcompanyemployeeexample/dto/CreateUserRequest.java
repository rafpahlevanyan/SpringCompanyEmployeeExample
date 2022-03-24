package com.example.springcompanyemployeeexample.dto;


import com.example.springcompanyemployeeexample.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateUserRequest {
    private int id;
    private String name;
    private String surname;
    private String userEmail;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}