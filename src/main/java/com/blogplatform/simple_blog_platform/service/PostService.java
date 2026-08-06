package com.blogplatform.simple_blog_platform.service;

import com.blogplatform.simple_blog_platform.exception.ResourceNotFoundException;
import com.blogplatform.simple_blog_platform.model.Post;
import com.blogplatform.simple_blog_platform.model.User;
import com.blogplatform.simple_blog_platform.repository.PostRepository;
import com.blogplatform.simple_blog_platform.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    @Transactional
    public Post savePost(Post post, String username) {

        if (post.getId() == null) {
            User author = userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Cannot find user with username " + username));

            post.setUser(author);
            post.setCreatedAt(LocalDateTime.now());

            return postRepository.save(post);
        } else {

            Post existingPost = postRepository.findById(post.getId()).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + post.getId()));

            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());

            return postRepository.save(existingPost);
        }

    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}