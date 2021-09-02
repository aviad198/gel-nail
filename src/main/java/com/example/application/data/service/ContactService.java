package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.TypeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactService {
    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
    private ContactRepository contactRepository;
    private CompanyRepository companyRepository;
    private ServiceRepository serviceRepository;

    public ContactService(ContactRepository contactRepository,
                          CompanyRepository companyRepository,
                          ServiceRepository serviceRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public long count() {
        return contactRepository.count();
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void save(Contact contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

}
