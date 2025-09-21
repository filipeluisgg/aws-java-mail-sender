package com.felipe.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_USERS")
public class UserModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

