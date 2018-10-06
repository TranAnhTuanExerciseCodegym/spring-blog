package com.codegym.blog.site;


import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CategorySiteController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findByAll();
    }


    @GetMapping("categories/{id}")
    public ModelAndView viewPostCategory(
            @PathVariable("id") Long id,
            Pageable pageable
    ) {
        Category category = categoryService.findById(id);
        ModelAndView modelAndView;
        if (category == null) {
            modelAndView = new ModelAndView("/blog/error");
        } else {
            Page<Post> posts = postService.findByCategory(category, pageable);
            modelAndView = new ModelAndView("/blog/category/view");
            modelAndView.addObject("category", category);
            modelAndView.addObject("posts", posts);
        }
        return modelAndView;
    }
}
