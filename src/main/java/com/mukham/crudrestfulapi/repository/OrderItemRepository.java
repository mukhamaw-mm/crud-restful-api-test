package com.mukham.crudrestfulapi.repository;

import com.mukham.crudrestfulapi.model.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    @Query(nativeQuery = true, value = "select * from order_items where order_no=:orderNo")
    List<OrderItems> findByOrderNo(@Param("orderNo") long orderNo);
}
