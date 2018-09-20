package com.codegym.blog.service;

import com.codegym.blog.model.Category;
import com.codegym.blog.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Iterable<Category> findByAll();

    Category findById(Long id);

    void save(Category category);

    void remove(Long id);

}
