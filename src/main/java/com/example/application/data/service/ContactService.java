package com.example.application.data.service;

import com.example.application.data.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/*
    @PostConstruct
    public void populateTestData() {

        int seed = 123;
        /*
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
            company1.setName("medical nail");
            company1.setAddress(addresses.get(r.nextInt(addresses.size())));
            company1.setDescription("Manicure and medical pedicure");
            company1.setMainImageURL("https://stayglam.com/wp-content/uploads/2018/12/Black-and-Pink-Glitter.jpg");
            company1.setMail("company1@comapny1.com");
            companyRepository.save(company1);

            Company company2 = new Company();
            company2.setRating(3);
            company2.setName("The beauty of the gel");
            company2.setAddress(addresses.get(r.nextInt(addresses.size())));
            company2.setDescription("Gel nail polish, nail construction and manicure");
            company2.setMainImageURL("https://i.pinimg.com/originals/15/6e/e5/156ee52dcdc2a7555ca2c8714e31b147.jpg");
            company2.setMail("company2@comapny2.com");
            companyRepository.save(company2);

            Company company3 = new Company();
            company3.setRating(4);
            company3.setName("Stunning gel");
            company3.setAddress(addresses.get(r.nextInt(addresses.size())));
            company3.setDescription("Our services are pedicures, manicures and gel nail polishes");
            company3.setMainImageURL("https://cdn.shopify.com/s/files/1/0962/4412/products/bluesky-gel-polish-blue-bamboo-neon32-bright-cobalt-neon-solid-cosmetics-finger-nail-916_1024x1024_65a21905-012b-4021-b84a-08bcdeb2f4c8_600x.jpg?v=1614951718");
            company3.setMail("company3@comapny3.com");
            companyRepository.save(company3);

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

    }*/
}