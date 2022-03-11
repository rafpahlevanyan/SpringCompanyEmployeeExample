package com.example.springcompanyemployeeexample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private double salary;
    private String position;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "employee")
    private List<EmployeeImage> employeeImages;
    @ManyToMany
    @JoinTable(
            name = "employee_languages",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languages;

}