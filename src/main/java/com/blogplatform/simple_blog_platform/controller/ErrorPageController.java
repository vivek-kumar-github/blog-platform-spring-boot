package com.blogplatform.simple_blog_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/error/403")
    public String showForbiddenPage() {
        return "error/403";
    }
}