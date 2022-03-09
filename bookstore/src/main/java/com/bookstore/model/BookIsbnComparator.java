package com.bookstore.model;

import java.util.Comparator;

public class BookIsbnComparator implements Comparator<Book> {

    @Override
    public int compare(Book firstBook, Book secondBook) {
        return firstBook.getIsbn().compareTo(secondBook.getIsbn());
    }

}
