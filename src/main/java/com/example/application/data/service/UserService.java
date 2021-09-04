package com.example.application.data.service;

import com.example.application.data.entity.User;
import com.vaadin.flow.component.textfield.TextField;
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

    public User find(String username) {
        return repository.findByUsername(username);
    }

    public User getUser(User user) {
        return repository.getOne(user.getId());
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
