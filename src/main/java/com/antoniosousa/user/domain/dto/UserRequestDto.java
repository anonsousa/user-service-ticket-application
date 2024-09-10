package com.antoniosousa.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {

    @Size(min = 2, max = 80)
    private @NotBlank  String username;
    @Size(min = 8, max = 120)
    private @NotBlank String email;
    @Size(min = 12, max = 25)
    private @NotBlank String phone;
    @Size(min = 2, max = 150)
    private @NotBlank String password;

}
