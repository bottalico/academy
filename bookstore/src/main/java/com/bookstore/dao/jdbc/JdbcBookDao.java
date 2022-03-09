package com.bookstore.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bookstore.dao.BookDao;
import com.bookstore.model.Author;
import com.bookstore.model.Book;

public class JdbcBookDao implements BookDao {
    private Connection conn;

    public JdbcBookDao(Connection conn) {
        this.conn = conn;
    }

    private Book buildBook(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        BigDecimal price = rs.getBigDecimal("price");
        Long isbn = rs.getLong("isbn");
        int year = rs.getInt("year");
        Long publisherId = rs.getLong("publisher_id");
        Book book = new Book(id, title, price, isbn, year, publisherId);
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book;");
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                books.add(buildBook(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(long id) {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book WHERE id = ?;")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(buildBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> searched = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book WHERE title = ?;")) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    searched.add(buildBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searched;
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        List<Book> searched = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM book b JOIN book_author ba ON b.id = ba.book_id JOIN author a ON ba.author_id = a.id WHERE a.id = ?;")) {
            pstmt.setLong(1, author.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    searched.add(buildBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searched;
    }

    @Override
    public Book saveOrUpdate(Book book) {
        Book result = null;
        try {
            conn.setAutoCommit(false);
            if (book.getId() != null) {
                Optional<Book> currBook = findById(book.getId());
                if (currBook.isPresent()) {
                    result = currBook.get();
                }
            }
            if (result != null) {
                result.setTitle(book.getTitle());
                result.setPrice(book.getPrice());
                result.setIsbn(book.getIsbn());
                result.setYear(book.getYear());
                result.setPublisherId(book.getPublisherId());
                try (PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE book SET title = ?, price = ?, isbn = ?, `year` = ?, publisher_id = ? WHERE id = ?;")) {
                    pstmt.setString(1, result.getTitle());
                    pstmt.setBigDecimal(2, result.getPrice());
                    pstmt.setLong(3, result.getIsbn());
                    pstmt.setInt(4, result.getYear());
                    pstmt.setLong(5, result.getPublisherId());
                    pstmt.setLong(6, result.getId());
                    pstmt.executeUpdate();
                }
            } else {
                if (book.getId() != null) {
                    result = new Book(book.getId(), book.getTitle(), book.getPrice(), book.getIsbn(), book.getYear(),
                            book.getPublisherId());
                    try (PreparedStatement pstmt = conn
                            .prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?);")) {
                        pstmt.setLong(1, result.getId());
                        pstmt.setString(2, result.getTitle());
                        pstmt.setBigDecimal(3, result.getPrice());
                        pstmt.setLong(4, result.getIsbn());
                        pstmt.setInt(5, result.getYear());
                        pstmt.setLong(6, result.getPublisherId());
                        pstmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(
                            "INSERT INTO book (title, price, isbn, `year`, publisher_id) VALUES (?, ?, ?, ?, ?);",
                            Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setBigDecimal(2, book.getPrice());
                        pstmt.setLong(3, book.getIsbn());
                        pstmt.setInt(4, book.getYear());
                        pstmt.setLong(5, book.getPublisherId());
                        pstmt.executeUpdate();
                        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                            generatedKeys.next();
                            result = new Book(generatedKeys.getLong(1), book.getTitle(), book.getPrice(),
                                    book.getIsbn(), book.getYear(), book.getPublisherId());
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                /* Nothing to do */
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                /* Nothing to do */
            }
        }
        return result;
    }

}
