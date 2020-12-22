package com.mukham.crudrestfulapi.repository;

import com.mukham.crudrestfulapi.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = "select * from product where id=:id and is_deleted=false")
    Product findbyId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from product where is_deleted=false")
    List<Product> findall();

}
