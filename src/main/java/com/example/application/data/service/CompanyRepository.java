package com.example.application.data.service;

import com.example.application.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("select c from Company c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.rating) like lower(concat('%', :searchTerm, '%'))")
    List<Company> search(@Param("searchTerm") String searchTerm);

    @Query("select c from Company c ORDER BY (c.rating) DESC")
    List<Company> sortByRating();

    @Query("select c from Company c ORDER BY (c.name) ASC")
    List<Company> sortByNameAZ();

    @Query("select c from Company c ORDER BY (c.name) DESC")
    List<Company> sortByNameZA();

    @Query("select c from Company c " +
            "where lower(c.address.city) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.address.country) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.address.city) like lower(concat('%', :searchTerm, '%'))")
    List<Company> searchByAddress(@Param("searchTerm")String addressVal);
}