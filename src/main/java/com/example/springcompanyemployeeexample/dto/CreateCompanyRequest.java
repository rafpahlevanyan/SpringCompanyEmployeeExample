package com.example.springcompanyemployeeexample.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateCompanyRequest {
    private int id;
    @NotEmpty
    private String name;
    @NotNull
    private int size;
    @NotEmpty
    private String address;

}