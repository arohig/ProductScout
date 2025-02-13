package com.activity.product_scout.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.activity.product_scout.services.MessageProcessorService;

@Component
public class MessageConsumer {
    private static final System.Logger logger = System.getLogger(MessageConsumer.class.getName());

    @Autowired
    private MessageProcessorService messageProcessorService;

    /* Consume messages from the Kafka topic */
    @KafkaListener(topics = "products", groupId = "consumer_group")
    public void consume(String message) {
        logger.log(System.Logger.Level.INFO, "Message consumed: {0}", message);
        messageProcessorService.processMessage(message);
    }
}
