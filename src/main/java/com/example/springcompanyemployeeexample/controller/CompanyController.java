package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.service.CompanyService;
import com.example.springcompanyemployeeexample.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyController {


    private final CompanyService companyService;

    private final EmployeeService employeeService;


    @GetMapping("/companies")
    public String employeePage(ModelMap map) {
        List<Company> companies = companyService.findAll();
        map.addAttribute("companies", companies);
        return "companies";
    }

    @GetMapping("/addCompany")
    public String addCompanyPage() {
        return "saveCompany";
    }

    @PostMapping("/addCompany")
    public String addCompany(@ModelAttribute Company company) {
        companyService.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        Company company = companyService.getById(id);
        employeeService.deleteEmployeeByCompany(company);
        companyService.deleteById(id);
        return "redirect:/companies";
    }


}