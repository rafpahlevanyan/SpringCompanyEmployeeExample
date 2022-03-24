package com.example.springcompanyemployeeexample.service;

import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.Employee;
import com.example.springcompanyemployeeexample.entity.EmployeeImage;
import com.example.springcompanyemployeeexample.entity.Language;
import com.example.springcompanyemployeeexample.repository.CompanyRepository;
import com.example.springcompanyemployeeexample.repository.EmployeeImageRepository;
import com.example.springcompanyemployeeexample.repository.EmployeeRepository;
import com.example.springcompanyemployeeexample.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Value("${companyemployee.upload.path}")
    private String imgPath;
    private final EmployeeRepository employeeRepository;
    private final LanguageRepository languageRepository;
    private final EmployeeImageRepository employeeImageRepository;

    public List<Employee> deleteEmployeeByCompany(Company company) {
        return employeeRepository.deleteEmployeeByCompany(company);
    }


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAllByCompany(Company company) {
        return employeeRepository.findAllByCompany(company);
    }

    public Employee getById(int id) {
        return employeeRepository.getById(id);
    }



    public Employee addEmployee(Employee employee, MultipartFile[] uploadedFiles, List<Integer> languages) throws IOException {
        List<Language> languagesFromDB = getLanguagesFromRequest(languages);
        employee.setLanguages(languagesFromDB);
        employeeRepository.save(employee);
        saveEmployeeImages(uploadedFiles, employee);
        return employee;
    }


    private void saveEmployeeImages(MultipartFile[] uploadedFiles, Employee employee) throws IOException {

        if (uploadedFiles.length != 0) {

            for (MultipartFile uploadedFile : uploadedFiles) {
                if (!uploadedFile.getOriginalFilename().equals("")) {
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
        }
    }


    private List<Language> getLanguagesFromRequest(List<Integer> languagesIds) {
        List<Language> languages = new ArrayList<>();
        for (Integer language : languagesIds) {
            languages.add(languageRepository.getById(language));
        }
        return languages;
    }


}
