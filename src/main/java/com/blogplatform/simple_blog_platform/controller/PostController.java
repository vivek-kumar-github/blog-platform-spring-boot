package com.blogplatform.simple_blog_platform.controller;

import com.blogplatform.simple_blog_platform.model.Post;
import com.blogplatform.simple_blog_platform.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String showHomePage(Model model) {

        List<Post> allPosts = postService.findAllPosts();

        model.addAttribute("posts", allPosts);

        return "home";
    }

}
