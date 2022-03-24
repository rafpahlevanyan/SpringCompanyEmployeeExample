package com.example.springcompanyemployeeexample.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateCompanyRequest {
    private int id;
    private String name;
    private int size;
    private String address;

}