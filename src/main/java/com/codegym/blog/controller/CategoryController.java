package com.codegym.blog.controller;

import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ModelAndView showCategoryForm() {
        Iterable<Category> categories = categoryService.findByAll();
        ModelAndView modelAndView = new ModelAndView("/blog/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCategoryCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCategory(
            @ModelAttribute("category") Category category
    ) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/blog/category/create");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "New category has been created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCategoryForm(
            @PathVariable("id") Long id
    ) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/category/edit");
            modelAndView.addObject("category", category);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/blog/404");
            return modelAndView;
        }
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateCategory(
            @ModelAttribute("category") Category category
    ) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/blog/category/edit");
        modelAndView.addObject("message", "Edit category successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCategory(
            @PathVariable("id") Long id
    ) {
        Category category = categoryService.findById(id);
        ModelAndView modelAndView;
        if (category != null) {
           modelAndView = new ModelAndView("/blog/category/delete");
            modelAndView.addObject("category", category);
        } else {
           modelAndView = new ModelAndView("/blog/404");
        }
        return modelAndView;
    }

    @PostMapping("delete/{id}")
    public String removeCategory(
            @ModelAttribute("category") Category category,
            RedirectAttributes redirectAttributes
    ) {
        categoryService.remove(category.getId());
        redirectAttributes.addFlashAttribute("message", "Delete category successfully");
        return "redirect:/admin/categories";
    }
}
