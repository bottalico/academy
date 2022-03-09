package com.bookstore.api.repository;

import com.bookstore.model.Author;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
