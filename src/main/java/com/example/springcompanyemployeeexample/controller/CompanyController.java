package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.dto.CreateCompanyRequest;
import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.security.CurrentUser;
import com.example.springcompanyemployeeexample.service.CompanyService;
import com.example.springcompanyemployeeexample.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String addCompany(@ModelAttribute CreateCompanyRequest createCompanyRequest,@AuthenticationPrincipal CurrentUser currentUser) {

        companyService.addCompanyFromCompanyRequest(createCompanyRequest,currentUser.getUser());
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        Company company = companyService.getById(id);
        employeeService.deleteEmployeeByCompany(company);
        companyService.deleteById(id);
        return "redirect:/companies";
    }

    @GetMapping("/myCompanies")
    public String companiesByCurrentUserPage(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Company> companies = companyService.findAllByUser(user);
        map.addAttribute("companies", companies);
        return "companies";
    }


}