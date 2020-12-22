package com.mukham.crudrestfulapi.model.entity;


import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "QUANTITY")
    int quantity;

    @Column(name = "TOTAL")
    double total;

    @Column(name = "ORDER_NO")
    Long orderNo;

    @Column(name = "PRODUCT_ID")
    long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
