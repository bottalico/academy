package com.bookstore.dao.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.bookstore.dao.PublisherDao;
import com.bookstore.model.Publisher;

public class JpaPublisherDao implements PublisherDao {
    EntityManager em;

    public JpaPublisherDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Publisher> findAll() {
        TypedQuery<Publisher> q = em.createQuery("SELECT p FROM Publisher p", Publisher.class);
        return q.getResultList();
    }

    @Override
    public Optional<Publisher> findById(long id) {
        return Optional.ofNullable(em.find(Publisher.class, id));
    }

    @Override
    public Publisher saveOrUpdate(Publisher publisher) {
        Publisher result = null;
        em.getTransaction().begin();
        if (publisher.getId() != null) {
            result = em.merge(publisher);
        } else {
            em.persist(publisher);
            result = publisher;
        }
        em.getTransaction().commit();
        return result;
    }

}
