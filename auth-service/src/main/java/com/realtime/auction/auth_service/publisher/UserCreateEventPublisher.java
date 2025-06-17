package com.realtime.auction.auth_service.publisher;

import com.realtime.auction.auth_service.event.LoginEvent;
import com.realtime.auction.sharedlib.event.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserCreateEventPublisher {
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
    private final String topic;

    public UserCreateEventPublisher(
            KafkaTemplate<String, UserCreatedEvent> kafkaTemplate,
            @Value("${app.kafka.topics.user-created}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publish(UserCreatedEvent event) {
        kafkaTemplate.send(topic, event.getUserId().toString(), event);
    }
}