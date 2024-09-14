package com.bloger.web.blog.models;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String role;

    public UserEntity(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}