package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.repository.CompanyRepository;
import com.example.springcompanyemployeeexample.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/companies")
    public String employeePage(ModelMap map) {
        List<Company> companies = companyRepository.findAll();
        map.addAttribute("companies", companies);
        return "companies";
    }

    @GetMapping("/addCompany")
    public String addCompanyPage(){
        return "saveCompany";
    }

    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        Company company = companyRepository.findById(id).get();
        employeeRepository.deleteEmployeeByCompany(company);
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }



}