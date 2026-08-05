package com.blogplatform.simple_blog_platform.controller;

import com.blogplatform.simple_blog_platform.model.Post;
import com.blogplatform.simple_blog_platform.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PostService postService;

    public AdminController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String showPostListDashboard(Model model) {

        List<Post> allPost = postService.findAllPosts();

        model.addAttribute("posts", allPost);

        return "admin/list-posts";
    }

    @GetMapping("/posts/new")
    public String showNewPostForm(Model model) {

        Post post = new Post();

        model.addAttribute("post", post);

        return "admin/post-form";
    }

}
