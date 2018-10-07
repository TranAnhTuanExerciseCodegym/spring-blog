package com.codegym.blog.site;

import com.codegym.blog.model.Post;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostSiteController {
    @Autowired
    private PostService postService;

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
