package com.mukham.crudrestfulapi.service;

import com.mukham.crudrestfulapi.model.entity.Product;
import com.mukham.crudrestfulapi.model.request.OrderRequest;
import com.mukham.crudrestfulapi.model.response.CheckOrderResponse;
import com.mukham.crudrestfulapi.model.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerOrderService {
    OrderResponse insertProduct(String productName, String price);
    List<Product> getAllProduct();
    OrderResponse orderCheck(OrderRequest request);
    OrderResponse confirmOrder(CheckOrderResponse request);
    OrderResponse getOrderDetails(long orderNumber);
    OrderResponse updateStatus(String orderStatus, long orderNumber);
    OrderResponse deleteProduct(Long id);
}
