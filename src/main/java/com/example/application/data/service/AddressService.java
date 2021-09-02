package com.example.application.data.service;

import com.example.application.data.entity.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class AddressService extends CrudService<Address, Integer> {

    private AddressRepository repository;
    private CompanyRepository companyRepository;

    public AddressService(@Autowired AddressRepository repository, CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    @Override
    protected AddressRepository getRepository() {
        return repository;
    }

}
