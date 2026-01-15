package com.picoids.Synchronously_message.service;

import com.picoids.Synchronously_message.Product;
import com.picoids.Synchronously_message.ProductCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    KafkaTemplate<String,ProductCreateEvent > kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(Product product) throws Exception{



        String productId = UUID.randomUUID().toString();

         final Logger log = LoggerFactory.getLogger(this.getClass());

        ProductCreateEvent productCreateEvent = new ProductCreateEvent(productId,
                product.getTitle(),
                product.getPrice(),
                product.getQuantity());

        log.info("Before publishing a ProductCreatedEvent");

             SendResult<String,ProductCreateEvent> result =
                    kafkaTemplate.send("product-created-synchronously-event-topic",productId,productCreateEvent).get();
                log.info("Partition: "+ result.getRecordMetadata().partition());
                log.info("Topic: " + result.getRecordMetadata().topic());
                log.info("Ofset: " + result.getRecordMetadata().offset());
                log.info("****** Returning product Id");
                return productId;
    }
}
