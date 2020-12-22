package com.mukham.crudrestfulapi.service;

import com.mukham.crudrestfulapi.model.entity.Customer;
import com.mukham.crudrestfulapi.model.entity.Order;
import com.mukham.crudrestfulapi.model.entity.OrderItems;
import com.mukham.crudrestfulapi.model.entity.Product;
import com.mukham.crudrestfulapi.model.request.OrderRequest;
import com.mukham.crudrestfulapi.model.response.CheckOrderResponse;
import com.mukham.crudrestfulapi.model.response.ConfirmOrderResponse;
import com.mukham.crudrestfulapi.model.response.OrderResponse;
import com.mukham.crudrestfulapi.model.response.Status;
import com.mukham.crudrestfulapi.repository.CustomerRepository;
import com.mukham.crudrestfulapi.repository.OrderItemRepository;
import com.mukham.crudrestfulapi.repository.OrderRepository;
import com.mukham.crudrestfulapi.repository.ProductRepository;
import com.mukham.crudrestfulapi.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author Mu Kham Aw.
 * @Description this is customer order process and crud process .
 */

@Service
public class CustomerOrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    private static final Logger logger = LogManager.getLogger(CustomerOrderService.class);

    public OrderResponse insertProduct(String productName, String price) {

        OrderResponse response = new OrderResponse();

        if (!ValidationUtil.isValidString(productName)) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Product Name is null or empty: " + productName);

            response.setStatus(status);
            return response;

        }
        if (!ValidationUtil.isValidString(price)) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Unit pirce is null or empty: " + price);

            response.setStatus(status);
            return response;
        }

        double unitPrice = Double.parseDouble(price);

        Product product = new Product();
        product.setName(productName);
        product.setUnitPrice(unitPrice);
        product.setDelete(false);

        productRepository.save(product);

        Status status = new Status();
        status.setStatus("Success");
        response.setStatus(status);
        return response;

    }

    public List<Product> getAllProduct() {
        List<Product> productList = productRepository.findall();
        return productList;

    }

    public OrderResponse orderCheck(OrderRequest request) {

        OrderResponse response = new OrderResponse();

        //validation the input fields
        boolean b = ValidationUtil.validate(request);


        if (!b) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Input field is null or empty or format is something wrong!");
            response.setStatus(status);
            return response;
        }

        Product product = productRepository.findbyId(request.getProductId());
        if (product == null) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("There is no product for this product Id: " + request.getProductId());
            response.setStatus(status);
            return response;
        }

        //getting total value
        double total = product.getUnitPrice() * request.getQuantity();

        CheckOrderResponse checkResponse = new CheckOrderResponse();

        checkResponse.setCustomerName(request.getCustomerName());
        checkResponse.setCustomerAddress(request.getCustomerAddress());
        checkResponse.setCustomerEmail(request.getCustomerEmail());
        checkResponse.setCustomerPhone(request.getCustomerPhone());
        checkResponse.setProductId(request.getProductId());
        checkResponse.setQuantity(request.getQuantity());
        checkResponse.setTotal(total);

        Status status = new Status();
        status.setStatus("Success");

        response.setStatus(status);
        response.setData(checkResponse);

        return response;

    }

    @Transactional
    public OrderResponse confirmOrder(CheckOrderResponse request) {

        OrderResponse response = new OrderResponse();
        OrderRequest req = new OrderRequest();
        req.setCustomerName(request.getCustomerName());
        req.setCustomerAddress(request.getCustomerAddress());
        req.setCustomerEmail(request.getCustomerEmail());
        req.setCustomerPhone(request.getCustomerPhone());
        req.setQuantity(request.getQuantity());
        req.setProductId(request.getProductId());
        boolean b = ValidationUtil.validate(req);
        if (!b) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Input field is null or empty or format is something wrong!");
            response.setStatus(status);
            return response;
        }

        //find by email to know duplicate record or already in db
        List<Customer> customerList = customerRepository.findByEmail(request.getCustomerEmail());

        if (customerList.size() > 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Duplicate customer record: " + request.getCustomerEmail());
            response.setStatus(status);
            return response;
        }

        //avoid not to save if customer is already in database.
        if (customerList.size() == 0) {
            Customer customer = new Customer();
            customer.setName(request.getCustomerName());
            customer.setAddress(request.getCustomerAddress());
            customer.setEmail(request.getCustomerEmail());
            customer.setPhone(request.getCustomerPhone());
            //save customer information
            customerRepository.save(customer);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        String rno = String.format("%04d", random.nextInt(10000));

        String date = simpleDateFormat.format(new Date());
        //generating order number by date time format  and 4 digit of random number
        String orderNo = date + rno;

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("Pending");
        Long orderNumber = Long.parseLong(orderNo);
        order.setOrderNo(orderNumber);
        order.setCustomerEmail(request.getCustomerEmail());
        orderRepository.save(order);

        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(request.getQuantity());
        orderItems.setTotal(request.getTotal());
        orderItems.setOrderNo(orderNumber);
        orderItems.setProductId(request.getProductId());
        orderItemRepository.save(orderItems);

        Status status = new Status();
        status.setStatus("Success");

        response.setStatus(status);
        response.setData(orderNumber);


        return response;

    }

    public OrderResponse getOrderDetails(long orderNumber) {

        OrderResponse response = new OrderResponse();
        List<Order> orderList = orderRepository.findByOrderNo(orderNumber);

        if (orderList.size() > 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Duplicate customer records for this order no: " + orderNumber);

            response.setStatus(status);
            return response;
        }
        if (orderList.size() < 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Not found for Order with this order no: " + orderNumber);

            response.setStatus(status);
            return response;
        }

        Order order = orderList.get(0);

        List<Customer> customerList = customerRepository.findByEmail(order.getCustomerEmail());

        if (customerList.size() > 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Duplicate customer records for this order no: " + order.getCustomerEmail());
            response.setStatus(status);
            return response;
        }

        if (customerList.size() < 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Not found for customer with this email: " + order.getCustomerEmail());
            response.setStatus(status);
            return response;
        }

        Customer customer = customerList.get(0);

        List<OrderItems> orderItemsList = orderItemRepository.findByOrderNo(orderNumber);
        if (orderItemsList.size() > 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Duplicate order items records for this order no: " + orderNumber);
            response.setStatus(status);
            return response;
        }

        if (orderItemsList.size() < 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Not found for order items with this order no: " + orderNumber);
            response.setStatus(status);
            return response;
        }

        OrderItems orderItems = orderItemsList.get(0);

        Product product = productRepository.findbyId(orderItems.getProductId());
        if (product == null) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Not found for Product with this product id: " + orderItems.getProductId());
            response.setStatus(status);
            return response;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        ConfirmOrderResponse orderResponse = new ConfirmOrderResponse();
        orderResponse.setOrderNo(orderNumber);
        orderResponse.setCustomerName(customer.getName());
        orderResponse.setCustomerEmail(customer.getEmail());
        orderResponse.setCustomerPhone(customer.getPhone());
        orderResponse.setCustomerAddress(customer.getAddress());
        orderResponse.setOrderCreatedDate(simpleDateFormat.format(order.getOrderDate()));
        orderResponse.setOrderStatus(order.getStatus());
        orderResponse.setQuantity(orderItems.getQuantity());
        orderResponse.setTotal(orderItems.getTotal());
        orderResponse.setProductName(product.getName());

        Status status = new Status();
        status.setStatus("Success");
        response.setStatus(status);
        response.setData(orderResponse);

        return response;

    }

    public OrderResponse updateStatus(String orderStatus, long orderNumber) {
        OrderResponse response = new OrderResponse();
        List<Order> orderList = orderRepository.findByOrderNo(orderNumber);

        if (orderList.size() > 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Duplicate customer records for this order no: " + orderNumber);

            response.setStatus(status);
            return response;
        }
        if (orderList.size() < 1) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Not found for Order with this order no: " + orderNumber);

            response.setStatus(status);
            return response;
        }

        Order order = orderList.get(0);
        order.setStatus(orderStatus);

        orderRepository.save(order);

        Status status = new Status();
        status.setStatus("Success");
        response.setStatus(status);

        return response;

    }

    public OrderResponse deleteProduct(Long id) {

        OrderResponse response = new OrderResponse();
        Product product = productRepository.findbyId(id);
        if (product == null) {
            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage("Not found for Product with this product id: " + id);
            response.setStatus(status);
            return response;
        }
        product.setDelete(true);
        productRepository.save(product);

        Status status = new Status();
        status.setStatus("Success");
        response.setStatus(status);

        return response;
    }


}
