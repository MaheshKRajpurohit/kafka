package com.example.products;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.picoids.core.ProductCreateEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{



    private final Logger log = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreateEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }




    @Override
    public String createProduct(CreateProductRestModel productRestModel) {

        String productId = UUID.randomUUID().toString();

        ProductCreateEvent productCreatedEvent = new ProductCreateEvent(productId,
                productRestModel.getTitle(),
                productRestModel.getPrice(),
                productRestModel.getQuantity());

        CompletableFuture<SendResult<String,ProductCreateEvent>> future  = kafkaTemplate.send("product-created-events-topic",productId,productCreatedEvent);

        future.whenComplete((result,ex) -> {
            if(ex != null){
                log.error("******* Failed to send message: "+ex.getMessage());
            }else {
                log.info("******* Message sent successfully: "+result.getRecordMetadata());
            }
        });

        log.info("*******  Returning the productId");

        return productId;
    }
}
