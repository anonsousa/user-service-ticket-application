package com.antoniosousa.user.domain.mapper;

import com.antoniosousa.user.domain.dto.UserRequestDto;
import com.antoniosousa.user.domain.dto.UserResponseDto;
import com.antoniosousa.user.domain.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "integrated", ignore = true)
    @Mapping(target = "notified", ignore = true)
    UserEntity toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "password", source = "password")
    UserResponseDto toDto(UserEntity userEntity);

    List<UserResponseDto> toDtoList(List<UserEntity> userEntities);

}
