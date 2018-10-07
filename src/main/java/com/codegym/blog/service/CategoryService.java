package com.codegym.blog.service;

import com.codegym.blog.model.Category;

public interface CategoryService {
    Iterable<Category> findByAll();

    Category findById(Long id);

    void save(Category category);

    void remove(Long id);

    Iterable<Category> findAllByNameContaining(String name);
}
