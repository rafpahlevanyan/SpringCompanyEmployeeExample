package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.Employee;
import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.service.CompanyService;
import com.example.springcompanyemployeeexample.service.EmployeeService;
import com.example.springcompanyemployeeexample.service.UserService;
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
public class UserController {


    private final UserService userService;
    private final CompanyService companyService;

    private final EmployeeService employeeService;


    @GetMapping("/users")
    public String userPage(ModelMap map) {
        List<User> users = userService.findAll();
        map.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/addUser")
    public String addUserPage() {
        return "saveUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.getById(id);
        Company company = companyService.getById(id);
        employeeService.deleteEmployeeByCompany(company);
        companyService.deleteCompanyByUser(user);
        userService.deleteById(id);
        return "redirect:/users";
    }
    @GetMapping("/companies/byUsers/{id}")
    public String companiesByUsersPage(ModelMap map, @PathVariable("id") int id) {
        User user = userService.getById(id);
        List<Company> companies = companyService.findAllByUser(user);
        map.addAttribute("companies", companies);
        return "companies";
    }


}