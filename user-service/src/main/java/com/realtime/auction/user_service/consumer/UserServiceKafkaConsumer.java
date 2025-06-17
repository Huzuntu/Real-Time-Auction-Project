package com.realtime.auction.user_service.consumer;

import com.realtime.auction.sharedlib.event.UserCreatedEvent;
import com.realtime.auction.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceKafkaConsumer {
    private final UserService userService;

    @KafkaListener(topics = "${app.kafka.topics.user-created}", groupId = "user-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleUserCreated(UserCreatedEvent event) {
        userService.createUserProfile(event);
    }
}
