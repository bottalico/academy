package com.bookstore.api.controllers;

import java.util.Optional;

import com.bookstore.api.repository.AuthorRepository;
import com.bookstore.model.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    @GetMapping("/authors")
    public Iterable<Author> getAll() {
        return repository.findAll();
    }

    @GetMapping("/authors/{authorId}")
    public Optional<Author> getById(@PathVariable(value = "authorId") Long id) {
        return repository.findById(id);
    }

    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author) {
        return repository.save(author);
    }

    @PutMapping("/authors/{authorId}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(value = "authorId") Long id, @RequestBody Author author) {
        if (repository.existsById(id)) {
            author.setId(id);
            return ResponseEntity.ok(repository.save(author));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "authorId") Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
