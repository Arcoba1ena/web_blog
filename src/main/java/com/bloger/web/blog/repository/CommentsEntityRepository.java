package com.bloger.web.blog.repository;

import java.util.List;
import com.bloger.web.blog.models.CommentsEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentsEntityRepository extends CrudRepository<CommentsEntity, Long> {
    List<CommentsEntity> findByUserId(Long userId);
}