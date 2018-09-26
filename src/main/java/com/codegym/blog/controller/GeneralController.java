package com.codegym.blog.controller;


import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralController {
    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findByAll();
    }

    @GetMapping("/admin")
    public String homePage() {
        return "redirect:/admin/posts";
    }

    @GetMapping("/404")
    public ModelAndView showError404() {
        ModelAndView modelAndView = new ModelAndView("/blog/post/error-404");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    @GetMapping("view-post/{id}")
    public ModelAndView view(
            @PathVariable("id") Long id
    ) {
        Post post = postService.findById(id);
        ModelAndView modelAndView;
        if (post != null) {
            modelAndView = new ModelAndView("/view");
            modelAndView.addObject("post", post);
        } else {
            modelAndView = new ModelAndView("redirect:/admin");
        }
        return modelAndView;
    }
}
