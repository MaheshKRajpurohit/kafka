package com.picoids.Synchronously_message.service;

import com.picoids.Synchronously_message.Product;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService {

    String createProduct(Product product) throws Exception;
}
