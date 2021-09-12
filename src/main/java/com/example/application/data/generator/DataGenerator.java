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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringComponent
public class DataGenerator {
    private User user, admin1, admin2, admin3, admin4, admin5;
    private Address address1, address2, address3, address4, address5;
    private Company company1, company2, company3, company4, company5;
    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder,
                                      UserRepository userRepository,
                                      CompanyRepository companyRepository,
                                      BookingRepository bookingRepository,
                                      AddressRepository addressRepository,
                                      AddressService addressService) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());

            int seed = 123;

            logger.info("Generating demo data");


            if (addressRepository.count() == 0) {
                logger.info("... generating 5 addresses entities...");
                address1 = new Address();
                address1.setCity("Tel Aviv");
                address1.setCountry("Israel");
                address1.setStreet("Dizingoff 123");
                addressRepository.save(address1);

                address2 = new Address();
                address2.setCity("Haifa");
                address2.setCountry("Israel");
                address2.setStreet("Horev 10");
                addressRepository.save(address2);

                address3 = new Address();
                address3.setCity("Jerusalem");
                address3.setCountry("Israel");
                address3.setStreet("Ben Yehuda 20");
                addressRepository.save(address3);

                address4 = new Address();
                address4.setCity("Tel Aviv");
                address4.setCountry("Israel");
                address4.setStreet("Ben Yehuda 10");
                addressRepository.save(address4);

                address5 = new Address();
                address5.setCity("Haifa");
                address5.setCountry("Israel");
                address5.setStreet("Ben Yehuda 30");
                addressRepository.save(address5);

            }
            if (companyRepository.count() == 0) {
                logger.info("... generating 5 companies entities...");

                //   List<Address> addresses = addressRepository.findAll();
                List<User> users = userRepository.findAll();
                company1 = new Company();
                company1.setRating(1);
                company1.setName("Medical nail");
                company1.setAddress(address1);
                company1.setDescription("Manicure and medical pedicure");
                company1.setMainImageURL("https://stayglam.com/wp-content/uploads/2018/12/Black-and-Pink-Glitter.jpg");
                company1.setMail("company1@comapny1.com");
                companyRepository.save(company1);

                company2 = new Company();
                company2.setRating(3);
                company2.setName("The beauty of the gel");
                company2.setAddress(address2);
                company2.setDescription("Gel nail polish, nail construction and manicure");
                company2.setMainImageURL("https://i.pinimg.com/originals/15/6e/e5/156ee52dcdc2a7555ca2c8714e31b147.jpg");
                company2.setMail("company2@comapny2.com");
                companyRepository.save(company2);

                company3 = new Company();
                company3.setRating(4);
                company3.setName("Stunning gel");
                company3.setAddress(address3);
                company3.setDescription("Our services are pedicures, manicures and gel nail polishes");
                company3.setMainImageURL("https://cdn.shopify.com/s/files/1/0962/4412/products/bluesky-gel-polish-blue-bamboo-neon32-bright-cobalt-neon-solid-cosmetics-finger-nail-916_1024x1024_65a21905-012b-4021-b84a-08bcdeb2f4c8_600x.jpg?v=1614951718");
                company3.setMail("company3@comapny3.com");
                companyRepository.save(company3);

                company4 = new Company();
                company4.setRating(3);
                company4.setName("The beauty center");
                company4.setAddress(address4);
                company4.setDescription("Gel nail polish");
                company4.setMainImageURL("https://beauty-fr.htgetrid.com/images/article/orig/2017/07/fioletovyj-gel-lak-16.jpg");
                company4.setMail("company4@comapny4.com");
                companyRepository.save(company4);

                company5 = new Company();
                company5.setRating(5);
                company5.setName("The perfection of beauty");
                company5.setAddress(address5);
                company5.setDescription("With us you will feel like a princess");
                company5.setMainImageURL("https://liroshopli.co.il/wp-content/uploads/2020/10/%D7%9C%D7%A7-%D7%92%D7%B3%D7%9C-378-1.jpeg");
                company5.setMail("company5@comapny5.com");
                companyRepository.save(company5);

            }
            if (userRepository.count() == 0) {
                logger.info("... generating 6 User entities...");

                List<Company> companyList = companyRepository.findAll();

                user = new User();
                user.setFirstName("mor");
                user.setLastName("Noman");
                user.setUsername("user");
                user.setHashedPassword(passwordEncoder.encode("user"));
                user.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                user.setRoles(Collections.singleton(Role.USER));
                user.setEmail("user@user.com");
                userRepository.save(user);

                admin1 = new User();
                admin1.setFirstName("John");
                admin1.setLastName("Normal");
                admin1.setUsername("admin1");
                admin1.setHashedPassword(passwordEncoder.encode("admin1"));
                admin1.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                admin1.setRoles(Collections.singleton(Role.ADMIN));
                admin1.setEmail("admin1@admin1.com");
                admin1.setCompany(company1);
                userRepository.save(admin1);
                company1.setAdmin(admin1);

                admin2 = new User();
                admin2.setFirstName("or");
                admin2.setLastName("gal");
                admin2.setUsername("admin2");
                admin2.setHashedPassword(passwordEncoder.encode("admin2"));
                admin2.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                admin2.setRoles(Collections.singleton(Role.ADMIN));
                admin2.setEmail("admin2@admin2.com");
                admin2.setCompany(company2);
                userRepository.save(admin2);
                company2.setAdmin(admin2);

                admin3 = new User();
                admin3.setFirstName("tal");
                admin3.setLastName("aviv");
                admin3.setUsername("admin3");
                admin3.setHashedPassword(passwordEncoder.encode("admin3"));
                admin3.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                admin3.setRoles(Collections.singleton(Role.ADMIN));
                admin3.setEmail("admin3@admin3.com");
                admin3.setCompany(company3);
                userRepository.save(admin3);
                company3.setAdmin(admin3);

                admin4 = new User();
                admin4.setFirstName("poly");
                admin4.setLastName("david");
                admin4.setUsername("admin4");
                admin4.setHashedPassword(passwordEncoder.encode("admin4"));
                admin4.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                admin4.setRoles(Collections.singleton(Role.ADMIN));
                admin4.setEmail("admin4@admin4.com");
                admin4.setCompany(company4);
                userRepository.save(admin4);
                company4.setAdmin(admin4);

                admin5 = new User();
                admin5.setFirstName("gil");
                admin5.setLastName("mono");
                admin5.setUsername("admin5");
                admin5.setHashedPassword(passwordEncoder.encode("admin5"));
                admin5.setProfilePictureUrl("https://cdn.pixabay.com/photo/2017/06/13/12/53/profile-2398782_1280.png");
                admin5.setRoles(Collections.singleton(Role.ADMIN));
                admin5.setEmail("admin5@admin5.com");
                admin5.setCompany(company5);
                userRepository.save(admin5);
                company5.setAdmin(admin5);

                companyRepository.saveAll(companyList );

               /*

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

                */
            }

           logger.info("... generating 7 bookings entities...");
            List<Company> companyList = companyRepository.findAll();
            Booking booking1 = new Booking(companyList.get(0),user, LocalDate.now().atTime(10,0,0,0).plusDays(4));
            Booking booking2 = new Booking(companyList.get(0),admin1,LocalDate.now().atTime(11,0,0,0).plusDays(7));
            Booking booking3 = new Booking(companyList.get(1),user,LocalDate.now().atTime(12,0,0,0).plusDays(6));
            Booking booking4 = new Booking(companyList.get(1),admin2,LocalDate.now().atTime(13,0,0,0).plusDays(4));
            Booking booking5 = new Booking(companyList.get(2),user,LocalDate.now().atTime(14,0,0,0).plusDays(5));
            Booking booking6 = new Booking(companyList.get(2),admin2,LocalDate.now().atTime(15,0,0,0).plusDays(6));
            Booking booking7 = new Booking(companyList.get(2),user,LocalDate.now().atTime(16,0,0,0).plusDays(7));
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
