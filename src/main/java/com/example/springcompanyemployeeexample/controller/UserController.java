package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.dto.CreateUserRequest;
import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.entity.UserRole;
import com.example.springcompanyemployeeexample.service.CompanyService;
import com.example.springcompanyemployeeexample.service.EmployeeService;
import com.example.springcompanyemployeeexample.service.MailService;
import com.example.springcompanyemployeeexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

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
    public String addUser(@ModelAttribute @Valid CreateUserRequest createUserRequest,
                          BindingResult bindingResult,
                          ModelMap map, Locale locale) throws MessagingException {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            return "saveUser";
        } else {

            User user = mapper.map(createUserRequest, User.class);
            user.setActive(false);
            user.setToken(UUID.randomUUID().toString());
            user.setTokenCreatedDate(LocalDateTime.now());
            user.setUserRole(UserRole.USER);
            userService.save(user);
            mailService.sendHtmlEmail(user.getUserEmail(),
                    "Welcome " + user.getSurname(),
                    user, " http://localhost:8080/user/activate?token=" + user.getToken(), "verifyTemplate", locale);
            return "redirect:/users";
        }

    }


    @GetMapping("/user/activate")
    public String activateUser(ModelMap map, @RequestParam(name = "token") String token) {
        Optional<User> user = userService.findByToken(token);

        if (user.isEmpty()) {
            map.addAttribute("message", "User does not exist");
            return "activateUser";
        }
        User userFromDb = user.get();
        if (userFromDb.isActive()) {
            map.addAttribute("message", "User already active");
            return "activateUser";
        }
        userFromDb.setActive(true);
        userFromDb.setToken(null);
        userFromDb.setTokenCreatedDate(null);
        userService.save(userFromDb);
        map.addAttribute("message", "User activated ,please login");
        return "activateUser";
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