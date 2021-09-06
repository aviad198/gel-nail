package com.example.application.data.service;

import com.example.application.data.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class CompanyService extends CrudService<Company,Integer> {

    private CompanyRepository companyRepository;

    public CompanyService(@Autowired CompanyRepository companyRepository) {
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

    @Override
    protected CompanyRepository getRepository() {
        return companyRepository;
    }

    public List<Company> findAllSortByRating(){
        return companyRepository.sortByRating();
    }
    public List<Company> findAllSortByNameAZ(){
        return companyRepository.sortByName();
    }
    public List<Company> findAllSortByNameZA(){
        return companyRepository.sortByName2();
    }
}

