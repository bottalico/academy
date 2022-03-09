package com.bookstore.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.bookstore.dao.BookDao;
import com.bookstore.model.Author;
import com.bookstore.model.Book;

public class JpaBookDao implements BookDao {
    EntityManager em;

    public JpaBookDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b", Book.class);
        return q.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByTitle(String title) {
        TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class);
        q.setParameter("title", title);
        return q.getResultList();
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :id", Book.class);
        q.setParameter("id", author.getId());
        return q.getResultList();
    }

    @Override
    public Book saveOrUpdate(Book book) {
        Book result = null;
        em.getTransaction().begin();
        if (book.getId() != null) {
            result = em.merge(book);
        } else {
            em.persist(book);
            result = book;
        }
        em.getTransaction().commit();
        return result;
    }

}
