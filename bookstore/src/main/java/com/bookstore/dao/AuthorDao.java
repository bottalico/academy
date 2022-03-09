package com.bookstore.dao;

import java.util.List;
import java.util.Optional;

import com.bookstore.model.Author;

public interface AuthorDao {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Author saveOrUpdate(Author author);
}
