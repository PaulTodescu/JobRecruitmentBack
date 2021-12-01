package com.wt.jrs.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentDAO extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findCommentById(Long commentId);
}
