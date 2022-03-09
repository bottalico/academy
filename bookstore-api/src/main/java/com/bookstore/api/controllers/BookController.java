package com.bookstore.api.controllers;

import com.bookstore.api.repository.BookRepository;
import com.bookstore.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    @GetMapping
    public Iterable<Book> getAll() {
        return repository.findAll();
    }

}
