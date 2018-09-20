package com.codegym.blog.controller;


import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.model.PostForm;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.service.PostService;
import com.codegym.util.StorageUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("category")
    public Iterable<Category> categories() {
        return categoryService.findByAll();
    }


    @GetMapping("")
    public ModelAndView list() {
        Iterable<Post> posts = postService.findByAll();
        ModelAndView modelAndView = new ModelAndView("/blog/post/list");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }
}
