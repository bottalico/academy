package com.bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookstore.dao.PublisherDao;
import com.bookstore.model.Publisher;

public class JdbcPublisherDao implements PublisherDao {
    private Connection conn;

    public JdbcPublisherDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM publisher;");
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                publishers.add(new Publisher(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }

    @Override
    public Optional<Publisher> findById(long id) {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM publisher WHERE id = ?;")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Long thisId = rs.getLong("id");
                    String name = rs.getString("name");
                    return Optional.of(new Publisher(thisId, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Publisher saveOrUpdate(Publisher publisher) {
        Publisher result = null;
        try {
            conn.setAutoCommit(false);
            if (publisher.getId() != null) {
                Optional<Publisher> current = findById(publisher.getId());
                if (current.isPresent()) {
                    result = current.get();
                }
            }
            if (result != null) {
                result.setName(publisher.getName());
                try (PreparedStatement pstmt = conn
                        .prepareStatement("UPDATE publisher SET name = ? WHERE id = ?;")) {
                    pstmt.setString(1, result.getName());
                    pstmt.setLong(2, result.getId());
                    pstmt.executeUpdate();
                }
            } else {
                if (publisher.getId() != null) {
                    result = new Publisher(publisher.getId(), publisher.getName());
                    try (PreparedStatement pstmt = conn
                            .prepareStatement("INSERT INTO publisher(id, name) VALUES (?, ?);")) {
                        pstmt.setLong(1, result.getId());
                        pstmt.setString(2, result.getName());
                        pstmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(
                            "INSERT INTO publisher(name) VALUES (?);",
                            Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, publisher.getName());
                        pstmt.executeUpdate();
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        generatedKeys.next();
                        result = new Publisher(generatedKeys.getLong(1), publisher.getName());
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
