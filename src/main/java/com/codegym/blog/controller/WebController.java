package com.codegym.blog.controller;


import com.codegym.blog.model.Post;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ModelAndView home() {
        Iterable<Post> posts = postService.findByAll();
        ModelAndView modelAndView = new ModelAndView("blog/home");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }
}
