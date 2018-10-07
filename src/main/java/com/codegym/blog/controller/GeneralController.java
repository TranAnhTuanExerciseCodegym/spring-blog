package com.codegym.blog.controller;


import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralController {
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findByAll();
    }

    @GetMapping("/404")
    public ModelAndView showError404() {
        ModelAndView modelAndView = new ModelAndView("/blog/404");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }
}
