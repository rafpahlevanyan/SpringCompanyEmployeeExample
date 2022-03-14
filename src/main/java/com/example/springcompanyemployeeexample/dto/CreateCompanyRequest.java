package com.example.springcompanyemployeeexample.dto;


import com.example.springcompanyemployeeexample.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreateCompanyRequest {
    private int id;
    private String name;
    private int size;
    private String address;

}