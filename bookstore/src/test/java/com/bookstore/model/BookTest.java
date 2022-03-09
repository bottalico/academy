package com.bookstore.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
    private Book book;

    @Before
    public void initBook() {
        Author author1 = new Author(1L, "Mario", "Rossi");
        Author author2 = new Author(2L, "Giovanna", "Bianchi");
        Publisher publisher1 = new Publisher(1L, "A Publisher");
        this.book = new Book(1L, "Libro 1", new BigDecimal("10.50"), new Author[] { author1, author2 }, publisher1);
    }

    @Test
    public void testAddAuthor() {
        int initialSize = book.getAuthors().length;
        book.addAuthor(new Author(3L, "Carlo", "Verdi"));
        assertEquals(initialSize + 1, book.getAuthors().length);
    }

    @Test
    public void testAddCategory() {
        int initialSize = book.getCategories().length;
        book.addCategory(BookCategory.ARTS_AND_PHOTOGRAPHY);
        assertEquals(initialSize + 1, book.getCategories().length);
    }

    @Test
    public void testToCsv() {
        book.addCategory(BookCategory.OTHER);
        book.addCategory(BookCategory.ARTS_AND_PHOTOGRAPHY);
        String expected = "1,Libro 1,1;Mario;Rossi|2;Giovanna;Bianchi,1;A Publisher,10.50,OTHER|ARTS_AND_PHOTOGRAPHY";
        assertEquals(expected, book.toCsv(","));
    }

    @Test
    public void testAuthorsToCsv() {
        String expected = "1;Mario;Rossi|2;Giovanna;Bianchi";
        assertEquals(expected, book.authorsToCsv("|"));
    }

    @Test
    public void testCategoriesToCsv() {
        book.addCategory(BookCategory.OTHER);
        book.addCategory(BookCategory.ARTS_AND_PHOTOGRAPHY);
        String expected = "OTHER|ARTS_AND_PHOTOGRAPHY";
        assertEquals(expected, book.categoriesToCsv("|"));
    }

    @Test
    public void testFromCsv() {
        Book result = Book.fromCsv(
                "1,Libro 1,1;Mario;Rossi|2;Giovanna;Bianchi,1;A Publisher,10.50,OTHER|ARTS_AND_PHOTOGRAPHY", ",");
        assertEquals((Long) 1L, result.getId());
        assertEquals("Libro 1", result.getTitle());
        assertEquals(2, result.getAuthors().length);
        assertNotNull(result.getPublisher());
        assertEquals(2, result.getCategories().length);
    }

    @Test
    public void testFromCsvWithoutPublisher() {
        Book result = Book.fromCsv("1,Libro 1,1;Mario;Rossi|2;Giovanna;Bianchi,,10.50,OTHER|ARTS_AND_PHOTOGRAPHY", ",");
        assertEquals((Long) 1L, result.getId());
        assertEquals("Libro 1", result.getTitle());
        assertEquals(2, result.getAuthors().length);
        assertNull(result.getPublisher());
        assertEquals(2, result.getCategories().length);
    }

    @Test
    public void testAuthorsFromCsv() {
        Author[] result = Book.authorsFromCsv("1;Mario;Rossi|2;Giovanna;Bianchi", "\\|");
        assertEquals(2, result.length);
        assertNotNull(result[0]);
    }

    @Test
    public void testCategoriesFromCsv() {
        BookCategory[] result = Book.categoriesFromCsv("OTHER|ARTS_AND_PHOTOGRAPHY", "\\|");
        assertEquals(2, result.length);
        assertEquals(BookCategory.OTHER, result[0]);
        assertEquals(BookCategory.ARTS_AND_PHOTOGRAPHY, result[1]);
    }

    @Test
    public void testComparableEqual() {
        Book b1 = new Book(1L, "Libro 1", null);
        Book b2 = new Book(1L, "Libro 2", null);
        assertEquals(0, b1.compareTo(b2));
    }

    @Test
    public void testComparableLesser() {
        Book b1 = new Book(1L, "Libro 1", null);
        Book b2 = new Book(2L, "Libro 2", null);
        assertTrue(b1.compareTo(b2) < 0);
    }

    @Test
    public void testComparableGreather() {
        Book b1 = new Book(1L, "Libro 1", null);
        Book b2 = new Book(2L, "Libro 2", null);
        assertTrue(b2.compareTo(b1) > 0);
    }

    @Test
    public void testBookIsbnComparatorEqual() {
        Book b1 = new Book(1L, "Libro 1", null);
        Book b2 = new Book(2L, "Libro 2", null);
        b1.setIsbn(1234567L);
        b2.setIsbn(1234567L);
        BookIsbnComparator comparator = new BookIsbnComparator();
        assertEquals(0, comparator.compare(b1, b2));
    }

    @Test
    public void testBookIsbnComparatorLesser() {
        Book b1 = new Book(1L, "Libro 1", null);
        Book b2 = new Book(2L, "Libro 2", null);
        b1.setIsbn(1234567L);
        b2.setIsbn(2345678L);
        BookIsbnComparator comparator = new BookIsbnComparator();
        assertTrue(comparator.compare(b1, b2) < 0);
    }

    @Test
    public void testBookIsbnComparatorGreather() {
        Book b1 = new Book(1L, "Libro 1", null);
        Book b2 = new Book(2L, "Libro 2", null);
        b1.setIsbn(1234567L);
        b2.setIsbn(2345678L);
        BookIsbnComparator comparator = new BookIsbnComparator();
        assertTrue(comparator.compare(b2, b1) > 0);
    }

}
