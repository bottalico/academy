package com.bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookstore.dao.AuthorDao;
import com.bookstore.model.Author;

public class JdbcAuthorDao implements AuthorDao {
    private Connection conn;

    public JdbcAuthorDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM author;");
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                authors.add(new Author(id, firstName, lastName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author = null;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM author WHERE id = ?;")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Long thisId = rs.getLong("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    author = new Author(thisId, firstName, lastName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Author saveOrUpdate(Author author) {
        Author result = null;
        try {
            conn.setAutoCommit(false);
            if (author.getId() != null) {
                Optional<Author> current = findById(author.getId());
                if (current.isPresent()) {
                    result = current.get();
                }
            }
            if (result != null) {
                result.setFirstName(author.getFirstName());
                result.setLastName(author.getLastName());
                try (PreparedStatement pstmt = conn
                        .prepareStatement("UPDATE author SET first_name = ? AND last_name = ? WHERE id = ?;")) {
                    pstmt.setString(1, result.getFirstName());
                    pstmt.setString(2, result.getLastName());
                    pstmt.setLong(3, result.getId());
                    pstmt.executeUpdate();
                }
            } else {
                if (author.getId() != null) {
                    result = new Author(author.getId(), author.getFirstName(), author.getLastName());
                    try (PreparedStatement pstmt = conn
                            .prepareStatement("INSERT INTO author(id, first_name, last_name) VALUES (?, ?, ?);")) {
                        pstmt.setLong(1, result.getId());
                        pstmt.setString(2, result.getFirstName());
                        pstmt.setString(3, result.getLastName());
                        pstmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(
                            "INSERT INTO author(first_name, last_name) VALUES (?, ?);",
                            Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, author.getFirstName());
                        pstmt.setString(2, author.getLastName());
                        pstmt.executeUpdate();
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        generatedKeys.next();
                        result = new Author(generatedKeys.getLong(1), author.getFirstName(), author.getLastName());
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
