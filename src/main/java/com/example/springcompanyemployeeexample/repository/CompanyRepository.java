package com.example.springcompanyemployeeexample.repository;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {


    @Transactional
    List<Company> deleteCompanyByUser(User user);

    List<Company> findAllByUser(User user);

}