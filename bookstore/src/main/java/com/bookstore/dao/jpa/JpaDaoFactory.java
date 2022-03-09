package com.bookstore.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.BookDao;
import com.bookstore.dao.DaoFactory;
import com.bookstore.dao.PublisherDao;

public class JpaDaoFactory implements DaoFactory {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public JpaDaoFactory(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.em = emf.createEntityManager();
    }

    @Override
    public BookDao getBookDao() {
        return new JpaBookDao(em);
    }

    @Override
    public AuthorDao getAuthorDao() {
        return new JpaAuthorDao(em);
    }

    @Override
    public PublisherDao getPublisherDao() {
        return new JpaPublisherDao(em);
    }

    @Override
    public void close() throws Exception {
        em.close();
        emf.close();
    }

}
