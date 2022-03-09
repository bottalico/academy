package com.bookstore.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.model.Publisher;

import org.junit.Before;
import org.junit.Test;

public class BookStoreTest {
    private BookStore bookStore;

    @Before
    public void populateBookstore() {
        Author author1 = new Author(1L, "Mario", "Rossi");
        Author author2 = new Author(2L, "Giovanna", "Bianchi");
        Author author3 = new Author(3L, "Carlo", "Verdi");
        Publisher publisher1 = new Publisher(1L, "A Publisher");
        Book book1 = new Book(1L, "Libro 1", new BigDecimal("10.50"), new Author[] { author1, author2 }, publisher1);
        Book book2 = new Book(2L, "Libro 2", new BigDecimal("5.25"), new Author[] { author3 }, publisher1);
        Book book3 = new Book(3L, "Another", new BigDecimal("4.25"), new Author[] { author2 }, publisher1);
        this.bookStore = new BookStore();
        this.bookStore.addBook(book1);
        this.bookStore.addBook(book2);
        this.bookStore.addBook(book3);
    }

    @Test
    public void testAddBook() {
        int initialSize = this.bookStore.books.size();
        Author author1 = new Author(1L, "Maria", "Neri");
        Author author2 = new Author(2L, "Luca", "Morini");
        Publisher publisher1 = new Publisher(1L, "Another Publisher");
        Book book = new Book(100L, "Libro 3", new BigDecimal("4.99"), new Author[] { author1, author2 }, publisher1);
        this.bookStore.addBook(book);
        assertEquals(initialSize + 1, this.bookStore.books.size());
    }

    @Test
    public void testRemoveBook() {
        int initialSize = this.bookStore.books.size();
        this.bookStore.removeBook(new Book(1L, "Not important", null));
        assertEquals(initialSize - 1, this.bookStore.books.size());
    }

    @Test
    public void testRemoveNotExistentBook() {
        int initialSize = this.bookStore.books.size();
        this.bookStore.removeBook(new Book(-1L, "Not important", null));
        assertEquals(initialSize, this.bookStore.books.size());
    }

    @Test
    public void testSearchBooksByTitle() {
        String title = "Libro 1";
        Book[] searched = this.bookStore.searchBooksByTitle(title);
        assertEquals(1, searched.length);
        assertEquals(title, searched[0].getTitle());
    }

    @Test
    public void testSearchBooksByTitleNotFound() {
        Book[] searched = this.bookStore.searchBooksByTitle("This doesn't exist!");
        assertEquals(0, searched.length);
    }

    @Test
    public void testSearchBooksByAuthor() {
        Book[] searched = this.bookStore.searchBooksByAuthor(new Author(2L, "Not", "Important"));
        assertEquals(2, searched.length);
    }

    @Test
    public void testSearchBooksByAuthorNotFound() {
        Book[] searched = this.bookStore.searchBooksByAuthor(new Author(-1L, "Not", "Existent"));
        assertEquals(0, searched.length);
    }

    @Test
    public void testReadBooks() {
        BookStore result = new BookStore();
        result.readBooks(BookStoreTest.class.getResourceAsStream("/testBooks.csv"));
        assertEquals(5, result.books.size());
    }

    @Test
    public void testWriteBooksWithoutBooks() throws IOException {
        BookStore bs = new BookStore();
        byte[] result = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            bs.writeBooks(os);
            result = os.toByteArray();
        }
        assertEquals("", new String(result, Charset.forName("UTF-8")));
    }

    @Test
    public void testWriteBooks() throws IOException, URISyntaxException {
        BookStore bs = new BookStore();
        bs.readBooks(BookStoreTest.class.getResourceAsStream("/testBooks.csv"));
        URL url = BookStoreTest.class.getResource("/testBooks.csv");
        Path path = Path.of(url.toURI());
        String expected = Files.readString(path, Charset.forName("UTF-8"));

        byte[] result = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            bs.writeBooks(os);
            result = os.toByteArray();
        }
        assertEquals(expected, new String(result, Charset.forName("UTF-8")));
    }

}
