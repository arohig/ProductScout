package com.activity.product_scout.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    private static final System.Logger logger = System.getLogger(MessageProducer.class.getName());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /* Send message to Kafka topic */
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        logger.log(System.Logger.Level.INFO, "Message produced: {0}", message);
    }
}
