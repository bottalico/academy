package com.bookstore.dao;

import java.util.List;
import java.util.Optional;

import com.bookstore.model.Publisher;

public interface PublisherDao {
    List<Publisher> findAll();

    Optional<Publisher> findById(long id);

    Publisher saveOrUpdate(Publisher publisher);
}
