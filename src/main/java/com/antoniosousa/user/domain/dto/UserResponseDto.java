package com.antoniosousa.user.domain.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
}
