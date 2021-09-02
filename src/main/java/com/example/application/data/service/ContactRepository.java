package com.example.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.data.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}