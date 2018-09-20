package com.codegym.blog.service;

import com.codegym.blog.model.Post;

public interface PostService {
    Iterable<Post> findByAll();

    Post findById(Long id);

    void save(Post post);

    void remove(Long id);
}
