package com.example.application.data.service;

import com.example.application.data.entity.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return companyRepository.findAll();
        } else {
            return companyRepository.search(stringFilter);
        }
    }

}