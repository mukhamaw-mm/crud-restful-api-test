package com.mukham.crudrestfulapi.repository;

import com.mukham.crudrestfulapi.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(nativeQuery = true, value = "select * from customer where email=:email")
    List<Customer> findByEmail(@Param("email") String email);
}
