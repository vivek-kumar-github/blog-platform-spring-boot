package com.blogplatform.simple_blog_platform.repository;

import com.blogplatform.simple_blog_platform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
