package com.bloger.web.blog.models;

import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
@NoArgsConstructor
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String comment;

    public CommentsEntity(Long userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }
}