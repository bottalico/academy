package com.bookstore.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.bookstore.dao.AuthorDao;
import com.bookstore.model.Author;

public class JpaAuthorDao implements AuthorDao {
    EntityManager em;

    public JpaAuthorDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> q = em.createQuery("SELECT a FROM Author a", Author.class);
        return q.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author saveOrUpdate(Author author) {
        Author result = null;
        em.getTransaction().begin();
        if (author.getId() != null) {
            result = em.merge(author);
        } else {
            em.persist(author);
            result = author;
        }
        em.getTransaction().commit();
        return result;
    }

}
