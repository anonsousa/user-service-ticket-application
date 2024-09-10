package com.antoniosousa.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 80)
    private String username;
    @Column(unique = true, nullable = false, length = 120)
    private String email;
    @Column(unique = true, nullable = false, length = 25)
    private String phone;
    @Column(unique = true, nullable = false, length = 150)
    private String password;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    private boolean consumed;
}
