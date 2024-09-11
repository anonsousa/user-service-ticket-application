package com.antoniosousa.user.domain.service;

import com.antoniosousa.user.domain.dto.UserRequestDto;
import com.antoniosousa.user.domain.dto.UserResponseDto;
import com.antoniosousa.user.domain.mapper.UserMapper;
import com.antoniosousa.user.domain.model.UserEntity;
import com.antoniosousa.user.domain.repositories.UserRepository;
import com.antoniosousa.user.domain.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final RabbitNotificationService rabbitNotificationService;
    private final UserRepository userRepository;
    private final String exchange;

    public UserService(RabbitNotificationService rabbitNotificationService,
                       UserRepository userRepository,
                       @Value("${rabbitmq.registered.user.exchange}") String exchange) {
        this.rabbitNotificationService = rabbitNotificationService;
        this.userRepository = userRepository;
        this.exchange = exchange;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userRequestDto);
        userRepository.save(userEntity);

        sendToQueue(userEntity, exchange);

        return UserMapper.INSTANCE.toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserById(Long id) {
        return UserMapper.INSTANCE.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return UserMapper.INSTANCE.toDtoList(userEntities);
    }


    private void sendToQueue(UserEntity userEntity, String exchange) {
        try {

            rabbitNotificationService.sendToQueue(userEntity, exchange);
            userRepository.updateIntegratedStatus(userEntity.getId(), true);

        } catch (RuntimeException exception) {

            userRepository.updateIntegratedStatus(userEntity.getId(), false);
        }
    }
}
