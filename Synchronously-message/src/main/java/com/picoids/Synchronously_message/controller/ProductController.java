package com.picoids.Synchronously_message.controller;

import com.picoids.Synchronously_message.Product;
import com.picoids.Synchronously_message.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product){



         String productId;
        try{
              productId = service.createProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(), e.getMessage(), "there is some issue "));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
