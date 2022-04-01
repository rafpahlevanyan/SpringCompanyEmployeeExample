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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String employeePage(ModelMap map, @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "3") int size) {


        PageRequest pageRequest = PageRequest.of(page, size , Sort.by("id").descending());
        Page<Employee> employeePage = employeeService.findAll(pageRequest);
        map.addAttribute("employeePage", employeePage);

        int totalPages = employeePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            map.addAttribute("pageNumbers", pageNumbers);
        }
        return "employees";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imgPath + picName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/employees/byCompanies/{id}")
    public String employeesByCompaniesPage(ModelMap map, @PathVariable("id") int id,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "3") int size) {
        Company company = companyService.getById(id);
        PageRequest pageRequest = PageRequest.of(page, size , Sort.by("id").descending());
        Page<Employee> employeePage = employeeService.findAllByCompany(company,pageRequest);
        map.addAttribute("employeePage", employeePage);
        int totalPages = employeePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            map.addAttribute("pageNumbers", pageNumbers);
        }
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
    public String addEmployee(@ModelAttribute @Valid
                              CreateEmployeeRequest createEmployeeRequest,
                              BindingResult bindingResult,
                              @RequestParam("pictures") MultipartFile[] uploadedFiles,
                              ModelMap map
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            map.addAttribute("languages", languageService.findAll());
            map.addAttribute("companies", companyService.findAll());
            return "saveEmployee";
        } else {
            Employee employee = mapper.map(createEmployeeRequest, Employee.class);
            employeeService.addEmployee(employee, uploadedFiles, createEmployeeRequest.getLanguages());
        }
        return "redirect:/employees";
    }

}