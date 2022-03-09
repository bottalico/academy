package com.bookstore.dao;

import java.sql.SQLException;

import com.bookstore.dao.jdbc.JdbcDaoFactory;
import com.bookstore.dao.jpa.JpaDaoFactory;

public class DaoFactoryCreator {
    public static final DaoFactory getJdbcDaoFactory() throws SQLException {
        return new JdbcDaoFactory("jdbc:mysql://localhost:3306/bookstore", "root", "admin01");
    }

    public static final DaoFactory getDaoFactory() {
        return new JpaDaoFactory("JPAbookstore");
    }

}
