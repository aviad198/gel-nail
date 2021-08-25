package com.example.application.data.service;
import com.example.application.data.entity.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<TypeService, Integer> {
    @Query("select s from TypeService s " +
            "where s.name =:service")
    List<TypeService> search(@Param("service") String service);
}
