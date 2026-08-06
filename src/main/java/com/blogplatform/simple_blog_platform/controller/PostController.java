package com.blogplatform.simple_blog_platform.controller;

import com.blogplatform.simple_blog_platform.dto.CommentDto;
import com.blogplatform.simple_blog_platform.model.Post;
import com.blogplatform.simple_blog_platform.service.CommentService;
import com.blogplatform.simple_blog_platform.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String showHomePage(Model model, @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "keyword", required = false) String keyword) {

        Page<Post> postPage;

        if (keyword != null && !keyword.isBlank()) {

            postPage = postService.searchByTitle(keyword, pageable);

            model.addAttribute("keyword", keyword);
        } else {
            postPage = postService.findAllPosts(pageable);
        }

        model.addAttribute("postPage", postPage);

        return "home";
    }

    @GetMapping("/posts/{id}")
    public String showPostDetailPage(@PathVariable Long id, Model model) {

        Post post = postService.findPostById(id);

        model.addAttribute("post", post);

        model.addAttribute("newComment", new CommentDto());

        return "post-detail";
    }

    @PostMapping("/posts/{postId}/comments")
    public String submitComment(@PathVariable Long postId, @Valid @ModelAttribute("newComment") CommentDto commentDto, BindingResult bindingResult, Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            Post post = postService.findPostById(postId);

            model.addAttribute("post", post);

            model.addAttribute("newComment", commentDto);

            return "post-detail";
        }
        String username = principal.getName();

        commentService.saveComment(postId, username, commentDto);

        return "redirect:/posts/" + postId;
    }

}
