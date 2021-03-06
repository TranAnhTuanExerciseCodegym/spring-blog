package com.codegym.blog.controller;


import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.model.PostForm;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.service.PostService;
import com.codegym.util.StorageUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findByAll();
    }


    @GetMapping("")
    public ModelAndView list(
            @RequestParam("search")Optional<String>search,
            Pageable pageable
    ) {
        Page<Post> posts;
        if (search.isPresent()) {
            posts = postService.findAllByTitleContaining(search.get(), pageable);
        } else {
            posts = postService.findByAll(pageable);
        }
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
        String randomCode = UUID.randomUUID().toString();
        String originFileName = postForm.getImage().getOriginalFilename();
        String randomName = randomCode + StorageUnits.getFileExtension(originFileName);
        try {
            postForm.getImage().transferTo(new File(StorageUnits.FEATURE_LOCATION + "/" + randomName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Post post = new Post();
        Date now = new Date();
        post.setTitle(postForm.getTitle());
        post.setCategory(postForm.getCategory());
        post.setContent(postForm.getContent());
        post.setDescription(postForm.getDescription());
        post.setImageUrl(randomName);
        post.setCreatedDate(now);

        postService.save(post);

        modelAndView.addObject("postForm", new PostForm());
        modelAndView.addObject("message", "New post has been created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditPostForm(
            @PathVariable("id") Long id
    ) {
        Post post = postService.findById(id);
        PostForm postForm = new PostForm();

        postForm.setId(post.getId());
        postForm.setTitle(post.getTitle());
        postForm.setCategory(post.getCategory());
        postForm.setContent(post.getContent());
        postForm.setDescription(post.getDescription());
        postForm.setImageUrl(post.getImageUrl());

        ModelAndView modelAndView;
        if (post != null) {
            modelAndView = new ModelAndView("/blog/post/edit");
            modelAndView.addObject("postForm", postForm);
        } else {
            modelAndView = new ModelAndView("/blog/404");
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updatePost(
            @PathVariable("id") Long id,
            @ModelAttribute("postForm") PostForm postForm
    ) {
        ModelAndView modelAndView = new ModelAndView("/blog/post/edit");
        Post post = postService.findById(id);

        if (!postForm.getImage().isEmpty()) {
            StorageUnits.removeFeature(post.getImageUrl());
            String randomCode = UUID.randomUUID().toString();
            String originFileName = postForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUnits.getFileExtension(originFileName);
            try {
                postForm.getImage().transferTo(new File(StorageUnits.FEATURE_LOCATION + "/" + randomName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.setImageUrl(randomName);
            postForm.setImageUrl(randomName);
        }

        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
        post.setCategory(postForm.getCategory());
        post.setContent(postForm.getContent());

        postService.save(post);
        modelAndView.addObject("postForm", postForm);
        modelAndView.addObject("message", "Update post successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeletePostForm(
            @PathVariable("id") Long id
    ) {
        Post post = postService.findById(id);
        ModelAndView modelAndView;
        if (post != null) {
            modelAndView = new ModelAndView("/blog/post/delete");
            modelAndView.addObject("post", post);
        } else {
            modelAndView = new ModelAndView("/blog/404");
        }
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String removePost(
            @ModelAttribute("post") Post post,
            RedirectAttributes redirectAttributes
    ) {
        postService.remove(post.getId());
        redirectAttributes.addFlashAttribute("message", "Remove post successfully");
        return "redirect:/admin/posts";
    }
}
