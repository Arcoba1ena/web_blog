package com.bloger.web.blog.repository;

import com.bloger.web.blog.models.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostEntityRepository extends CrudRepository<PostEntity, Long> {
}