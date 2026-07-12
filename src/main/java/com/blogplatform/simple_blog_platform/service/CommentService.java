package com.blogplatform.simple_blog_platform.service;

import com.blogplatform.simple_blog_platform.repository.CommentRepository;
import org.springframework.stereotype.Service;
import com.blogplatform.simple_blog_platform.model.Comment;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }
}
