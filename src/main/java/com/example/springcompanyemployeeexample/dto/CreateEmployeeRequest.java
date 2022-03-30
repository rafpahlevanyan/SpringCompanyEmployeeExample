package com.example.springcompanyemployeeexample.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;
    @NotNull(message = "PLease input valid salary")
    private double salary;
    @NotEmpty
    private String position;
    @NotNull
    private int companyId;
    @NotEmpty
    private List<Integer> languages;

}
