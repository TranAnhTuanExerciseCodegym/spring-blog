package com.codegym.blog.repository;

import com.codegym.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
