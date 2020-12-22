package com.mukham.crudrestfulapi.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ORDER_DETAIL")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    Long id;

    @Column(name="ORDER_NO")
    Long orderNo;

    @Column(name="ORDER_DATE")
    Date orderDate;

    @Column(name="STATUS")
    String status;

    @Column(name="CUSTOMER_EMAIL")
    String customerEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
