package com.bloger.web.blog.models;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String text;
    private String author;
    private String tag;
    private String comment;

    public PostEntity(String title, String text, String tag, String author, String comment) {
        this.title = title;
        this.text = text;
        this.tag = tag;
        this.author = author;
        this.comment = comment;
    }

    public PostEntity(String comment) {
        this.comment = comment;
    }
}