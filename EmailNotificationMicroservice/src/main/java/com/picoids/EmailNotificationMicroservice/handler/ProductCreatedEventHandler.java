package com.picoids.EmailNotificationMicroservice.handler;

import com.picoids.core.ProductCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "product-created-events-topic")
public class ProductCreatedEventHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(ProductCreateEvent productCreateEvent) {
        System.out.println("********* "+productCreateEvent.getTitle());
        log.info("Received a new event: {} {} {}" , productCreateEvent.getTitle(),productCreateEvent.getProductId(),productCreateEvent.getPrice());
    }
}
