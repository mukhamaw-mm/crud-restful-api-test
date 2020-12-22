package com.mukham.crudrestfulapi.repository;

import com.mukham.crudrestfulapi.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = "select * from order_detail where order_no=:orderNo")
    List<Order> findByOrderNo(@Param("orderNo") long orderNo);
}
