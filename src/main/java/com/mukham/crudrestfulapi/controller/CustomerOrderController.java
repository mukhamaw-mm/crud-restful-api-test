package com.mukham.crudrestfulapi.controller;

import com.mukham.crudrestfulapi.model.entity.Product;
import com.mukham.crudrestfulapi.model.request.OrderRequest;
import com.mukham.crudrestfulapi.model.response.CheckOrderResponse;
import com.mukham.crudrestfulapi.model.response.OrderResponse;
import com.mukham.crudrestfulapi.model.response.Status;
import com.mukham.crudrestfulapi.service.CustomerOrderServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerOrderController {

    @Autowired
    CustomerOrderServiceImp customerOrderService;

    private static final Logger logger = LogManager.getLogger(CustomerOrderController.class);

    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestParam String productName, @RequestParam String unitPrice) {

        logger.info("Start Add Product, productname: " + productName + ", unitPrice: " + unitPrice);
        OrderResponse response = new OrderResponse();
        try {

            Status status = new Status();
            status.setStatus("Success");

            customerOrderService.insertProduct(productName, unitPrice);

            response.setStatus(status);

            logger.info("Response: " + response);

            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            response.setStatus(status);

            logger.error("Response: " + response);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAllProduct")
    public ResponseEntity getAllProduct() {
        logger.info("Start findAll product");
        OrderResponse response = new OrderResponse();
        try {
            Status status = new Status();
            status.setStatus("Success");
            List<Product> productList = customerOrderService.getAllProduct();

            response.setStatus(status);
            response.setData(productList);

            logger.info("Response: " + response);

            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            response.setStatus(status);

            logger.error("Response: " + response);

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/checkOrder")
    public ResponseEntity orderCheck(@RequestBody OrderRequest request) {

        logger.info("Start check order process: " + request);

        try {
            OrderResponse orderResponse = customerOrderService.orderCheck(request);

            logger.info("Response: " + orderResponse);

            return new ResponseEntity(orderResponse, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            OrderResponse response = new OrderResponse();
            response.setStatus(status);

            logger.error("Response: " + response);

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/confirmOrder")
    public ResponseEntity confirmOrder(@RequestBody CheckOrderResponse request) {

        logger.info("Start confirm order process: " + request);

        try {

            OrderResponse response = customerOrderService.confirmOrder(request);

            logger.info("Response: " + response);

            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            OrderResponse response = new OrderResponse();
            response.setStatus(status);
            logger.error("Response: " + response);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getOrderDetail")
    public ResponseEntity getOrderDetail(@RequestParam long orderNo) {
        logger.info("Start get order deatail: " + orderNo);
        try {
            Status status = new Status();
            status.setStatus("Success");
            OrderResponse response = customerOrderService.getOrderDetails(orderNo);


            logger.info("Response: " + response);

            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            OrderResponse response = new OrderResponse();

            response.setStatus(status);

            logger.error("Response: " + response);

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/updateStatus")
    public ResponseEntity updateStatus(@RequestParam String orderStatus, @RequestParam long orderNo) {
        logger.info("Start update status process, orderstatus: " + orderStatus + ", orderNo: " + orderNo);
        try {
            Status status = new Status();
            status.setStatus("Success");
            OrderResponse response = customerOrderService.updateStatus(orderStatus, orderNo);


            logger.info("Response: " + response);

            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            OrderResponse response = new OrderResponse();

            response.setStatus(status);

            logger.error("Response: " + response);

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam long productId) {
        logger.info("Start delete product: " + productId);
        try {
            Status status = new Status();
            status.setStatus("Success");
            OrderResponse response = customerOrderService.deleteProduct(productId);


            logger.info("Response: " + response);

            return new ResponseEntity(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            Status status = new Status();
            status.setStatus("Fail");
            status.setMessage(e.getMessage());

            OrderResponse response = new OrderResponse();

            response.setStatus(status);

            logger.error("Response: " + response);

            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
