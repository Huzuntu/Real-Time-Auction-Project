package com.realtime.auction.auth_service.publisher;

import com.realtime.auction.auth_service.event.LoginEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginEventPublisher {
    private final KafkaTemplate<String, LoginEvent> kafkaTemplate;
    private final String topic;

    public LoginEventPublisher(
            KafkaTemplate<String, LoginEvent> kafkaTemplate,
            @Value("${app.kafka.topics.login}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publish(LoginEvent event) {
        kafkaTemplate.send(topic, event.getUserId().toString(), event);
    }
}