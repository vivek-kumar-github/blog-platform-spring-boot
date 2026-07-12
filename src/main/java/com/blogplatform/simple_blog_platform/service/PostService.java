package com.blogplatform.simple_blog_platform.service;

import com.blogplatform.simple_blog_platform.model.Post;
import com.blogplatform.simple_blog_platform.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public Post savePost(Post post) {
        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}