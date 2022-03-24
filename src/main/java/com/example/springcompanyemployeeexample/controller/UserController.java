package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.dto.CreateUserRequest;
import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.service.CompanyService;
import com.example.springcompanyemployeeexample.service.EmployeeService;
import com.example.springcompanyemployeeexample.service.MailService;
import com.example.springcompanyemployeeexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final MailService mailService;
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
    public String addUser(@ModelAttribute CreateUserRequest createUserRequest, ModelMap map) {
        User user = mapper.map(createUserRequest, User.class);
//        List<String> errorMsg = new ArrayList<>();
//        if (user.getName() == null || user.getName().equals("")) {
//            errorMsg.add("name is required");
//        }
//        if (user.getSurname() == null || user.getSurname().equals("")) {
//            errorMsg.add("surname is required");
//        }
//        if (!errorMsg.isEmpty()) {
//            map.addAttribute("errors", errorMsg);
//            return "saveUser";
//        }
        userService.addUserFromUserRequest(createUserRequest);
        mailService.sendMail(user.getUserEmail(), "Welcome", "You are registered " + user.getName());
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