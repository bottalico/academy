package com.bookstore.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AuthorTest {

    @Test
    public void testToCsv() {
        Author author = new Author(1L, "Mario", "Rossi");
        String expected = "1;Mario;Rossi";
        assertEquals(expected, author.toCsv(";"));
    }

    @Test
    public void testFromCsv() {
        Author result = Author.fromCsv("1;Mario;Rossi", ";");
        assertEquals((Long) 1L, result.getId());
        assertEquals("Mario", result.getFirstName());
        assertEquals("Rossi", result.getLastName());
    }

    @Test
    public void testComparableEqual() {
        Author a1 = new Author(1L, "Mario", "Rossi");
        Author a2 = new Author(1L, "Giovanna", "Bianchi");
        assertEquals(0, a1.compareTo(a2));
    }

    @Test
    public void testComparableLesser() {
        Author a1 = new Author(1L, "Mario", "Rossi");
        Author a2 = new Author(2L, "Giovanna", "Bianchi");
        assertTrue(a1.compareTo(a2) < 0);
    }

    @Test
    public void testComparableGreather() {
        Author a1 = new Author(1L, "Mario", "Rossi");
        Author a2 = new Author(2L, "Giovanna", "Bianchi");
        assertTrue(a2.compareTo(a1) > 0);
    }

}
