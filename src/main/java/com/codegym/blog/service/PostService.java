package com.codegym.blog.service;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<Post> findByAll(Pageable pageable);

    Post findById(Long id);

    void save(Post post);

    void remove(Long id);

    Page<Post> findByCategory(Category category, Pageable pageable);
}
