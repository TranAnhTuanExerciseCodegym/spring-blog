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

    @GetMapping("/create")
    public ModelAndView showCreatePostForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/post/create");
        modelAndView.addObject("postForm", new PostForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView savePost(
            @ModelAttribute("postForm") PostForm postForm
    ) {
        ModelAndView modelAndView = new ModelAndView("/blog/post/create");
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = postForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUnits.getFileExtension(originFileName);
            postForm.getImage().transferTo(new File(StorageUnits.FEATURE_LOCATION + "/" + randomName));

            Post post = new Post();
            post.setTitle(postForm.getTitle());
            post.setCategory(postForm.getCategory());
            post.setContent(postForm.getContent());
            post.setDescription(postForm.getDescription());
            post.setImageUrl(randomName);

            postService.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("postForm", new PostForm());
        modelAndView.addObject("message", "New post has been created successfully");
        return modelAndView;
    }
}
