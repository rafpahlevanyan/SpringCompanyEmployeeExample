package com.example.springcompanyemployeeexample.repository;

import com.example.springcompanyemployeeexample.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {


}