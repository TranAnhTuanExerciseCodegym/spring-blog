package com.codegym.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralController {
    @GetMapping("/admin")
    public String homePage() {
        return "redirect:/admin/posts";
    }

    @GetMapping("/404")
    public ModelAndView showError404() {
        ModelAndView modelAndView = new ModelAndView("/blog/post/error-404");
        return modelAndView;
    }
}
