package com.example.springcompanyemployeeexample.controller;

import com.example.springcompanyemployeeexample.dto.CreateEmployeeRequest;
import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.Employee;
import com.example.springcompanyemployeeexample.entity.EmployeeImage;
import com.example.springcompanyemployeeexample.entity.Language;
import com.example.springcompanyemployeeexample.repository.CompanyRepository;
import com.example.springcompanyemployeeexample.repository.EmployeeImageRepository;
import com.example.springcompanyemployeeexample.repository.EmployeeRepository;

import com.example.springcompanyemployeeexample.repository.LanguageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private EmployeeImageRepository employeeImageRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Value("${companyemployee.upload.path}")
    private String imgPath;


    @GetMapping("/employees")
    public String employeePage(ModelMap map) {
        List<Employee> employees = employeeRepository.findAll();
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
        Company company = companyRepository.getById(id);
        List<Employee> employees = employeeRepository.findAllByCompany(company);
        map.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies", companyRepository.findAll());
        map.addAttribute("languages", languageRepository.findAll());
        return "saveEmployee";
    }

    @GetMapping("/employees/{id}")
    public String singleEmployee(ModelMap map, @PathVariable int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);

        map.addAttribute("employee", employee);
        map.addAttribute("languages", languageRepository.findAll());
        return "singleEmployee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute CreateEmployeeRequest createEmployeeRequest,
                              @RequestParam("pictures") MultipartFile[] uploadedFiles) throws IOException {
        List<Language>languages = new ArrayList<>();
        for (Integer language : createEmployeeRequest.getLanguages()) {
            languages.add(languageRepository.getById(language));
        }
        Employee employee = Employee.builder()
                .id(createEmployeeRequest.getId())
                .name(createEmployeeRequest.getName())
                .surname(createEmployeeRequest.getSurname())
                .email(createEmployeeRequest.getEmail())
                .phone(createEmployeeRequest.getPhone())
                .salary(createEmployeeRequest.getSalary())
                .position(createEmployeeRequest.getPosition())
                .company(companyRepository.findById(createEmployeeRequest.getCompanyId()).orElse(null))
                .languages(languages)
                .build();
        employeeRepository.save(employee);
        if (uploadedFiles.length != 0) {
            for (MultipartFile uploadedFile : uploadedFiles) {
                String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
                File newFile = new File(imgPath + fileName);
                uploadedFile.transferTo(newFile);
                EmployeeImage employeeImage = EmployeeImage.builder()
                        .name(fileName)
                        .employee(employee)
                        .build();
                employeeImageRepository.save(employeeImage);
            }

        }
        return "redirect:/employees";
    }

}