package com.example.application.data.generator;

import com.example.application.data.Role;
import com.example.application.data.entity.*;
import com.example.application.data.service.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder,
                                      SampleFoodProductRepository sampleFoodProductRepository,
                                      UserRepository userRepository,
                                      CompanyRepository companyRepository,
                                      BookingRepository bookingRepository,
                                      ServiceRepository serviceRepository,
                                      AddressRepository addressRepository,
                                      AddressService addressService) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (sampleFoodProductRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            /*logger.info("... generating 100 Sample Food Product entities...");
            ExampleDataGenerator<SampleFoodProduct> sampleFoodProductRepositoryGenerator = new ExampleDataGenerator<>(
                    SampleFoodProduct.class, LocalDateTime.of(2021, 7, 25, 0, 0, 0));
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setId, DataType.ID);
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setImage, DataType.FOOD_PRODUCT_IMAGE);
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setName, DataType.FOOD_PRODUCT_NAME);
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setEanCode, DataType.FOOD_PRODUCT_EAN);
            sampleFoodProductRepository.saveAll(sampleFoodProductRepositoryGenerator.create(100, seed));
*/
            logger.info("... generating 10 addresses entities...");
/*            if (addressRepository.count() == 0) {
                ExampleDataGenerator<Address> addressRepositoryGenerator = new ExampleDataGenerator<>(
                        Address.class, LocalDateTime.of(2021, 8, 31, 0, 0, 0));
                addressRepositoryGenerator.setData(Address::setCity, DataType.CITY);
                addressRepositoryGenerator.setData(Address::setCountry, DataType.COUNTRY);
                addressRepositoryGenerator.setData(Address::setStreet, DataType.ADDRESS);
                addressRepositoryGenerator.setData(Address::setPostalCode, DataType.ZIP_CODE);
                addressRepository.saveAll(addressRepositoryGenerator.create(10, seed));

            }*/


            logger.info("... generating 2 User entities...");
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Noman");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            user.setEmail("user@user.com");
            userRepository.save(user);
            User admin = new User();
            admin.setFirstName("John");
            admin.setLastName("Normal");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            admin.setEmail("admin@admin.com");
            userRepository.save(admin);

            List<Company> companyList = companyRepository.findAll();
            if(companyList.size()>0)
                companyList.get(0).setAdmin(admin);
            companyRepository.save(companyList.get(0));

            if(companyList.size()>1)
                companyList.get(1).setAdmin(user);
            companyRepository.save(companyList.get(1));


          /*  List<Address> addressList = addressRepository.findAll();
            logger.info("... generating 6 company entities...");
            if (companyRepository.count() == 0) {
                Company company1 = new Company("Make my Nail",admin,"https://picsum.photos/200",new Random().nextInt(5) + 1,addressList.get(1));
                Company company2 = new Company("Aliza Nails",admin,"https://picsum.photos/200",new Random().nextInt(5) + 1,addressList.get(2));
                Company company3 = new Company("Top-Gel",admin,"https://picsum.photos/200",new Random().nextInt(5) + 1,addressList.get(3));
                Company company4 = new Company( "Lucky Nails",admin,"https://picsum.photos/200",new Random().nextInt(5) + 1,addressList.get(4));
                Company company5 = new Company("Polish",admin,"https://picsum.photos/200",new Random().nextInt(5) + 1,addressList.get(5));
                Company company6 = new Company("My Nails",admin,"https://picsum.photos/200",new Random().nextInt(5) + 1,addressList.get(6));
                companyRepository.save(company1);
                companyRepository.save(company2);
                companyRepository.save(company3);
                companyRepository.save(company4);
                companyRepository.save(company5);
                companyRepository.save(company6);*/

                //                companyRepository.saveAll(
//
//                        Stream.of("Make my Nail", "Aliza Nails", "Top-Gel", "Lucky Nails", "Polish", "My Nails")
//                                    .map(name -> {
//                                    Company company = new Company();
//                                    company.setName(name);
//                                    company.setAdmin(admin);
//                                    company.setMainImageURL("https://picsum.photos/200");
//                                    company.setRating(new Random().nextInt(5) + 1);
//                                    company.setAddress(addressList.get(new Random().nextInt(5) + 1));
//                                    return company;
//                                }).collect(Collectors.toList()));
//            }
            //logger.info("... generating 7 services entities...");

//            List<Company> companyList = companyRepository.findAll();

          /*  if (serviceRepository.count() == 0) {
                TypeService typeService1 = new TypeService("Polish", 40, 15, (Integer) companyList.get(0).getId(), companyList.get(0));
                TypeService typeService2 = new TypeService("manicure gel", 120, 70, (Integer) companyList.get(0).getId(), companyList.get(0));
                TypeService typeService3 = new TypeService("medical manicure", 230, 90, (Integer) companyList.get(1).getId(), companyList.get(1));
                TypeService typeService4 = new TypeService("manicure gel", 130, 70, (Integer) companyList.get(2).getId(), companyList.get(2));
                TypeService typeService5 = new TypeService("medical manicure", 220, 90, (Integer) companyList.get(2).getId(), companyList.get(2));
                TypeService typeService6 = new TypeService("Polish", 30, 15, (Integer) companyList.get(1).getId(), companyList.get(1));
                TypeService typeService7 = new TypeService("Polish", 50, 15, (Integer) companyList.get(2).getId(), companyList.get(2));
                serviceRepository.save(typeService1);
                serviceRepository.save(typeService2);
                serviceRepository.save(typeService3);
                serviceRepository.save(typeService4);
                serviceRepository.save(typeService5);
                serviceRepository.save(typeService6);
                serviceRepository.save(typeService7);
            }*/

    /*        logger.info("... generating 6 bookings entities...");
            Booking booking1 = new Booking(companyList.get(0),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking2 = new Booking(companyList.get(0),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking3 = new Booking(companyList.get(1),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking4 = new Booking(companyList.get(1),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking5 = new Booking(companyList.get(2),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking6 = new Booking(companyList.get(3),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking7 = new Booking(companyList.get(4),user,LocalDateTime.now().plusDays(2).plusHours(4));
            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingRepository.save(booking3);
            bookingRepository.save(booking4);
            bookingRepository.save(booking5);
            bookingRepository.save(booking6);
            bookingRepository.save(booking7);

            logger.info("Generated demo data");*/
        };
    }
}
