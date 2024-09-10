package com.antoniosousa.user.domain.service;

import com.antoniosousa.user.domain.model.UserEntity;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitNotificationService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitNotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(UserEntity user, String exchange, MessagePostProcessor messagePostProcessor) {
        rabbitTemplate.convertAndSend(exchange, "", user, messagePostProcessor);
    }

    public void sendToQueue(UserEntity user, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", user);
    }
}
