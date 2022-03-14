package com.example.springcompanyemployeeexample.service;

import com.example.springcompanyemployeeexample.dto.CreateCompanyRequest;
import com.example.springcompanyemployeeexample.entity.Company;
import com.example.springcompanyemployeeexample.entity.User;
import com.example.springcompanyemployeeexample.repository.CompanyRepository;
import com.example.springcompanyemployeeexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;


//    public Company save(Company company) {
//        return companyRepository.save(company);
//    }
    public Company addCompanyFromCompanyRequest(CreateCompanyRequest createCompanyRequest,User user){
        Company company = getCompanyFromRequest(createCompanyRequest);
        company.setUser(user);
        companyRepository.save(company);
        return company;
    }

    public void deleteById(int id) {
        companyRepository.deleteById(id);
    }

    public Company getById(int id) {
        return companyRepository.getById(id);
    }


    public List<Company> findAll() {
        return companyRepository.findAll();
    }


    public List<Company> deleteCompanyByUser(User user) {
        return companyRepository.deleteCompanyByUser(user);
    }


    public List<Company> findAllByUser(User user) {
        return companyRepository.findAllByUser(user);
    }

    private Company getCompanyFromRequest(CreateCompanyRequest createCompanyRequest){
        return  Company.builder()
                .id(createCompanyRequest.getId())
                .name(createCompanyRequest.getName())
                .size(createCompanyRequest.getSize())
                .address(createCompanyRequest.getAddress())
                .build();
    }

}
