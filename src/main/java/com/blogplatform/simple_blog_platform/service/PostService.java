package com.blogplatform.simple_blog_platform.service;

import com.blogplatform.simple_blog_platform.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
