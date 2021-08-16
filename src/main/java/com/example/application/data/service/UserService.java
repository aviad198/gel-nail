package com.example.application.data.service;

import com.example.application.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class UserService extends CrudService<User, Integer> {

    private UserRepository repository;

    public UserService(@Autowired UserRepository repository) {
        this.repository = repository;
    }
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

}
