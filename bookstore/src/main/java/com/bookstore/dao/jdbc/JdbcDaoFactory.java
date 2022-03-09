package com.bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.BookDao;
import com.bookstore.dao.DaoFactory;
import com.bookstore.dao.PublisherDao;

public class JdbcDaoFactory implements DaoFactory {
    private Connection conn;

    public JdbcDaoFactory(String connectionUrl, String username, String password) throws SQLException {
        this.conn = DriverManager.getConnection(connectionUrl, username, password);
    }

    @Override
    public BookDao getBookDao() {
        return new JdbcBookDao(conn);
    }

    @Override
    public AuthorDao getAuthorDao() {
        return new JdbcAuthorDao(conn);
    }

    @Override
    public PublisherDao getPublisherDao() {
        return new JdbcPublisherDao(conn);
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

}
