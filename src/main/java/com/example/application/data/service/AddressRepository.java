package com.example.application.data.service;

import com.example.application.data.entity.Address;
import com.example.application.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("select a.company from Address a " +
            "where lower(a.street) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.country) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.city) like lower(concat('%', :searchTerm, '%'))")
    List<Company> search(@Param("searchTerm") String searchTerm);
}