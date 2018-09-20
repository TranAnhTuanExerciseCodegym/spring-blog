package com.codegym.blog.controller;

import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ModelAndView showCategoryForm() {
        Iterable<Category> categories = categoryService.findByAll();
        ModelAndView modelAndView = new ModelAndView("/blog/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/category-create")
    public ModelAndView showCategoryCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/category-create")
    public ModelAndView saveCategory(
            @ModelAttribute("category") Category category
    ) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/blog/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditCategoryForm(
            @PathVariable("id") Long id
    ) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/category/edit");
            modelAndView.addObject("category", category);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/blog/category/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-category")
    public String updateCategory(
            @ModelAttribute("category") Category category,
            RedirectAttributes redirectAttributes
    ) {
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "edit category successfully");
        return "redirect:/category";
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView deleteCategory(
            @PathVariable("id") Long id
    ) {
        Category category = categoryService.findById(id);
        if (category != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/category/delete");
            modelAndView.addObject("category", category);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/blog/category/error-404");
            return modelAndView;
        }
    }

    @PostMapping("delete-category")
    public String removeCategory(
            @ModelAttribute("category") Category category,
            RedirectAttributes redirectAttributes
    ) {
        categoryService.remove(category.getId());
        redirectAttributes.addFlashAttribute("message", "delete category successfully");
        return "redirect:/category";
    }
}
