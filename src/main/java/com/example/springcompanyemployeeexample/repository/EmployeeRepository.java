package com.example.springcompanyemployeeexample.repository;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Transactional
    List<Employee> deleteEmployeeByCompany(Company company);

    List<Employee> findAllByCompany(Company company);

}