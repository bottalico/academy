package com.bookstore.dao;

import java.util.List;
import java.util.Optional;

import com.bookstore.model.Author;
import com.bookstore.model.Book;

public interface BookDao {
    List<Book> findAll();

    Optional<Book> findById(long id);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(Author author);

    Book saveOrUpdate(Book book);
}
