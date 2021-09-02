package com.example.application.data.service;

import com.example.application.data.entity.*;
import org.springframework.stereotype.Service;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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
    private AddressRepository addressRepository;
    private UserRepository userRepository;

    public ContactService(ContactRepository contactRepository,
                          CompanyRepository companyRepository,
                          ServiceRepository serviceRepository,
                          AddressRepository addressRepository,
                          UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.serviceRepository = serviceRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
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

    @PostConstruct
    public void populateTestData() {

        int seed = 123;

        if (addressRepository.count() == 0) {
            ExampleDataGenerator<Address> addressRepositoryGenerator = new ExampleDataGenerator<>(
                    Address.class, LocalDateTime.of(2021, 8, 31, 0, 0, 0));
            addressRepositoryGenerator.setData(Address::setState, DataType.STATE);
            addressRepositoryGenerator.setData(Address::setCity, DataType.CITY);
            addressRepositoryGenerator.setData(Address::setCountry, DataType.COUNTRY);
            addressRepositoryGenerator.setData(Address::setStreet, DataType.ADDRESS);
            addressRepositoryGenerator.setData(Address::setPostalCode, DataType.ZIP_CODE);
            addressRepository.saveAll(addressRepositoryGenerator.create(10, seed));

        }
        if (companyRepository.count() == 0) {
            Random r = new Random(0);
            List<Address> addresses = addressRepository.findAll();
            List<User> users = userRepository.findAll();
            Company company1 = new Company();
            company1.setRating(1);
            company1.setName("company1");
            company1.setAddress(addresses.get(r.nextInt(addresses.size())));
            company1.setDescription("desc1");
            company1.setMainImageURL("https://picsum.photos/200");
            companyRepository.save(company1);

            Company company2 = new Company();
            company2.setRating(1);
            company2.setName("company2");
            company2.setAddress(addresses.get(r.nextInt(addresses.size())));
            company2.setDescription("desc2");
            company2.setMainImageURL("https://picsum.photos/200");
            companyRepository.save(company2);
        }


            if (contactRepository.count() == 0) {
                Random r = new Random(0);
                List<Company> companies = companyRepository.findAll();
                List<Address> addresses = addressRepository.findAll();
                contactRepository.saveAll(
                        Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                                "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                                "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                                "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                                "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                                "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                                "Jaydan Jackson", "Bernard Nilsen")
                                .map(name -> {
                                    String[] split = name.split(" ");
                                    Contact contact = new Contact();
                                    contact.setFirstName(split[0]);
                                    contact.setLastName(split[1]);
                                    contact.setAddress(addresses.get(r.nextInt(addresses.size())));
                                    contact.setCompany(companies.get(r.nextInt(companies.size())));
                                    contact.setStatus(Contact.Status.values()[r.nextInt(Contact.Status.values().length)]);
                                    String email = (contact.getFirstName() + "." + contact.getLastName() + "@" + contact.getCompany().getName().replaceAll("[\\s-]", "") + ".com").toLowerCase();
                                    contact.setEmail(email);
                                    return contact;
                                }).collect(Collectors.toList()));
            }
        }

    }
