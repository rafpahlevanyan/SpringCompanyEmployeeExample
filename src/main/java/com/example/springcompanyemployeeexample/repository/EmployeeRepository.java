package com.example.springcompanyemployeeexample.repository;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Transactional
    List<Employee> deleteEmployeeByCompany(Company company);

    Page<Employee> findAllByCompany(Company company, Pageable pageable);

}