package com.example.application.data.generator;

import com.example.application.data.Role;
import com.example.application.data.entity.SampleFoodProduct;
import com.example.application.data.entity.User;
import com.example.application.data.service.SampleFoodProductRepository;
import com.example.application.data.service.UserRepository;
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

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder,
            SampleFoodProductRepository sampleFoodProductRepository, UserRepository userRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (sampleFoodProductRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Sample Food Product entities...");
            ExampleDataGenerator<SampleFoodProduct> sampleFoodProductRepositoryGenerator = new ExampleDataGenerator<>(
                    SampleFoodProduct.class, LocalDateTime.of(2021, 7, 25, 0, 0, 0));
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setId, DataType.ID);
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setImage, DataType.FOOD_PRODUCT_IMAGE);
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setName, DataType.FOOD_PRODUCT_NAME);
            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setEanCode, DataType.FOOD_PRODUCT_EAN);
            sampleFoodProductRepository.saveAll(sampleFoodProductRepositoryGenerator.create(100, seed));

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
            logger.info("Generated demo data");
        };
    }

}