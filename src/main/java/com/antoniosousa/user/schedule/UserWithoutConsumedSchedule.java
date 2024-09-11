package com.antoniosousa.user.schedule;

import com.antoniosousa.user.domain.repositories.UserRepository;
import com.antoniosousa.user.domain.service.RabbitNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UserWithoutConsumedSchedule {

    private final RabbitNotificationService rabbitNotificationService;
    private final UserRepository userRepository;
    private final String exchange;


    public UserWithoutConsumedSchedule(RabbitNotificationService rabbitNotificationService,
                                       UserRepository userRepository,
                                       @Value("${rabbitmq.registered.user.exchange}") String exchange) {
        this.rabbitNotificationService = rabbitNotificationService;
        this.userRepository = userRepository;
        this.exchange = exchange;
    }


    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void searchUsersWithoutConsumed() {

        log.info("Searching users without integrated and trying resend to queue");

        userRepository.findAllByIntegratedIsFalse().forEach(user -> {
            try {
                rabbitNotificationService.sendToQueue(user, exchange);
                userRepository.updateIntegratedStatus(user.getId(), true);

            } catch (RuntimeException exception) {

                log.info("Rabbit queue still offline");
                userRepository.updateIntegratedStatus(user.getId(), false);
            }
        });
    }

















}
