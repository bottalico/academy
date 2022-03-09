package com.bookstore.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PublisherTest {

    @Test
    public void testToCsv() {
        Publisher publisher = new Publisher(1L, "A publisher");
        String expected = "1;A publisher";
        assertEquals(expected, publisher.toCsv(";"));
    }

    @Test
    public void testFromCsv() {
        Publisher result = Publisher.fromCsv("1;A publisher", ";");
        assertEquals((Long) 1L, result.getId());
        assertEquals("A publisher", result.getName());
    }

}
