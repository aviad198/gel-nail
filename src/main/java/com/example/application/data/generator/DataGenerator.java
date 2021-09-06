package com.example.application.data.generator;

import com.example.application.data.Role;
import com.example.application.data.entity.Address;
import com.example.application.data.entity.Booking;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.service.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringComponent
public class DataGenerator {
    private User user, admin1, admin2, admin3;
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


            if (addressRepository.count() == 0) {
                logger.info("... generating 10 addresses entities...");
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
                logger.info("... generating 3 companies entities...");
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
            if (userRepository.count() == 0) {
                logger.info("... generating 4 User entities...");
                user = new User();
                user.setFirstName("John");
                user.setLastName("Noman");
                user.setUsername("user");
                user.setHashedPassword(passwordEncoder.encode("user"));
                user.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                user.setRoles(Collections.singleton(Role.USER));
                user.setEmail("user@user.com");
                userRepository.save(user);

                admin1 = new User();
                admin1.setFirstName("John");
                admin1.setLastName("Normal");
                admin1.setUsername("admin1");
                admin1.setHashedPassword(passwordEncoder.encode("admin1"));
                admin1.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                admin1.setRoles(Collections.singleton(Role.ADMIN));
                admin1.setEmail("admin1@admin1.com");
                userRepository.save(admin1);

                admin2 = new User();
                admin2.setFirstName("or");
                admin2.setLastName("gal");
                admin2.setUsername("admin2");
                admin2.setHashedPassword(passwordEncoder.encode("admin2"));
                admin2.setProfilePictureUrl("https://www.shareicon.net/data/512x512/2016/05/24/770117_people_512x512.png");
                admin2.setRoles(Collections.singleton(Role.ADMIN));
                admin2.setEmail("admin2@admin2.com");
                userRepository.save(admin2);

                admin3 = new User();
                admin3.setFirstName("tal");
                admin3.setLastName("aviv");
                admin3.setUsername("admin3");
                admin3.setHashedPassword(passwordEncoder.encode("admin3"));
                admin3.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                admin3.setRoles(Collections.singleton(Role.ADMIN));
                admin3.setEmail("admin3@admin3.com");
                userRepository.save(admin3);

                List<Company> companyList = companyRepository.findAll();

                if(companyList.size()>0) {
                    companyList.get(0).setAdmin(admin1);
                    companyRepository.save(companyList.get(0));
                    admin1.setCompany(companyList.get(0));
                    userRepository.save(admin1);
                }

                if(companyList.size()>1) {
                    companyList.get(1).setAdmin(admin2);
                    companyRepository.save(companyList.get(1));
                    admin2.setCompany(companyList.get(1));
                    userRepository.save(admin2);
                }

                if(companyList.size()>2) {
                    companyList.get(2).setAdmin(admin3);
                    companyRepository.save(companyList.get(2));
                    admin3.setCompany(companyList.get(2));
                    userRepository.save(admin3);
                }
            }


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

           logger.info("... generating 7 bookings entities...");
            List<Company> companyList = companyRepository.findAll();
            Booking booking1 = new Booking(companyList.get(0),user,LocalDateTime.now().plusDays(2).plusHours(4));
            Booking booking2 = new Booking(companyList.get(0),admin1,LocalDateTime.now().plusDays(3).plusHours(4));
            Booking booking3 = new Booking(companyList.get(1),user,LocalDateTime.now().plusDays(4).plusHours(4));
            Booking booking4 = new Booking(companyList.get(1),admin2,LocalDateTime.now().plusDays(5).plusHours(4));
            Booking booking5 = new Booking(companyList.get(2),user,LocalDateTime.now().plusDays(6).plusHours(4));
            Booking booking6 = new Booking(companyList.get(2),admin2,LocalDateTime.now().plusDays(7).plusHours(4));
            Booking booking7 = new Booking(companyList.get(2),user,LocalDateTime.now().plusDays(10).plusHours(4));
            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingRepository.save(booking3);
            bookingRepository.save(booking4);
            bookingRepository.save(booking5);
            bookingRepository.save(booking6);
            bookingRepository.save(booking7);

            logger.info("Generated demo data");
        };
    }
}
