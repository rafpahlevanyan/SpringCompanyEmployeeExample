package com.example.springcompanyemployeeexample.service;

import com.example.springcompanyemployeeexample.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

}
