package com.project.lendmate.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.lendmate.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @KafkaListener(topics = "user.deleted", groupId = "product-service")
    public void handleUserDeleted(String message) throws Exception {

        UserDeletedEvent event =
                objectMapper.readValue(message, UserDeletedEvent.class);

        log.info("User deleted event received for userId: {}",
                event.getUserId());

        productRepository.deleteAllByOwnerId(event.getUserId());
    }
}