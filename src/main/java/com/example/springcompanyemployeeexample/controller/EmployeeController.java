package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.dto.CreateEmployeeRequest;
import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.Employee;
import com.example.springcompanyemployeeexample.service.CompanyService;
import com.example.springcompanyemployeeexample.service.EmployeeService;
import com.example.springcompanyemployeeexample.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final ModelMapper mapper;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final LanguageService languageService;


    @Value("${companyemployee.upload.path}")
    private String imgPath;


    @GetMapping("/employees")
    public String employeePage(ModelMap map) {
        List<Employee> employees = employeeService.findAll();
        map.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imgPath + picName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/employees/byCompanies/{id}")
    public String employeesByCompaniesPage(ModelMap map, @PathVariable("id") int id) {
        Company company = companyService.getById(id);
        List<Employee> employees = employeeService.findAllByCompany(company);
        map.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies", companyService.findAll());
        map.addAttribute("languages", languageService.findAll());
        return "saveEmployee";
    }

    @GetMapping("/employees/{id}")
    public String singleEmployee(ModelMap map, @PathVariable int id) {
        Employee employee = employeeService.getById(id);

        map.addAttribute("employee", employee);
        map.addAttribute("languages", languageService.findAll());
        return "singleEmployee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute CreateEmployeeRequest createEmployeeRequest,
                              @RequestParam("pictures") MultipartFile[] uploadedFiles
    ) throws IOException {
        Employee employee = mapper.map(createEmployeeRequest, Employee.class);
        employeeService.addEmployee(employee, uploadedFiles, createEmployeeRequest.getLanguages());

        return "redirect:/employees";
    }

}