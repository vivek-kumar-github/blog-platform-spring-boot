package com.blogplatform.simple_blog_platform.repository;

import com.blogplatform.simple_blog_platform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
