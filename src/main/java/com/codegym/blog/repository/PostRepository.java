package com.codegym.blog.repository;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findByCategory(Category category, Pageable pageable);

    Page<Post> findAllByTitleContaining(String title, Pageable pageable);
}
