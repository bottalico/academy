package com.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "book")
public class Book implements Serializable, Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "isbn")
    private Long isbn;
    @Column(name = "year")
    private int year;

    @Transient
    private Long publisherId;

    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    @ManyToOne
    private Publisher publisher;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();
    @Transient
    private BookCategory[] categories = {};

    public Book() {
    }

    public Book(Long id, String title, BigDecimal price, Long isbn, int year, Long publisherId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.isbn = isbn;
        this.year = year;
        this.publisherId = publisherId;
    }

    public Book(Long id, String title, BigDecimal price, Author[] authors, Publisher publisher) {
        this.id = id;
        this.title = title;
        this.price = price;
        if (authors != null) {
            for (Author authorN : authors) {
                this.authors.add(authorN);
            }
        }
        this.publisher = publisher;
    }

    public Book(Long id, String title, BigDecimal price, Author[] authors) {
        this(id, title, price, authors, null);
    }

    public Book(Long id, String title, Author[] authors) {
        this(id, title, BigDecimal.ZERO, authors);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getIsbn() {
        return this.isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getPublisherId() {
        return this.publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Author[] getAuthors() {
        return this.authors.toArray(new Author[] {});
    }

    public void setAuthors(Author[] authors) {
        this.authors.clear();
        Collections.addAll(this.authors, authors);
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public BookCategory[] getCategories() {
        return this.categories;
    }

    public void setCategories(BookCategory[] categories) {
        this.categories = categories;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addCategory(BookCategory category) {
        this.categories = Arrays.copyOf(this.categories, this.categories.length + 1);
        this.categories[this.categories.length - 1] = category;
    }

    public String toCsv(String separator) {
        return String.format("%2$d%1$s%3$s%1$s%4$s%1$s%5$s%1$s%6$s%1$s%7$s", separator, id, title, authorsToCsv("|"),
                publisher != null ? publisher.toCsv(";") : "", price.toString(), categoriesToCsv("|"));
    }

    public String authorsToCsv(String separator) {
        StringBuilder result = new StringBuilder();

        if (this.authors.size() > 0) {
            result.append(this.authors.get(0).toCsv(";"));
            for (int i = 1; i < this.authors.size(); i++) {
                result.append(separator).append(this.authors.get(i).toCsv(";"));
            }
        }

        return result.toString();
    }

    public String categoriesToCsv(String separator) {
        StringBuilder result = new StringBuilder();

        if (this.categories.length > 0) {
            result.append(this.categories[0].name());
            for (int i = 1; i < this.categories.length; i++) {
                result.append(separator).append(this.categories[i].name());
            }
        }

        return result.toString();
    }

    public static Book fromCsv(String csv, String separator) {
        String[] fields = csv.split(separator);

        long id = Long.parseLong(fields[0]);
        String title = fields[1];
        Author[] authors = authorsFromCsv(fields[2], "\\|");
        Publisher publisher = "".equals(fields[3]) ? null : Publisher.fromCsv(fields[3], ";");
        BigDecimal price = new BigDecimal(fields[4]);
        BookCategory[] categories = fields.length < 6 ? new BookCategory[] {} : categoriesFromCsv(fields[5], "\\|");

        Book result = new Book(id, title, price, authors, publisher);

        for (BookCategory bookCategoryN : categories) {
            result.addCategory(bookCategoryN);
        }

        return result;
    }

    public static Author[] authorsFromCsv(String csv, String separator) {
        String[] fields = csv.split(separator);
        Author[] result = new Author[fields.length];

        for (int i = 0; i < fields.length; i++) {
            result[i] = Author.fromCsv(fields[i], ";");
        }

        return result;
    }

    public static BookCategory[] categoriesFromCsv(String csv, String separator) {
        String[] fields = csv.split(separator);
        BookCategory[] result = new BookCategory[fields.length];

        for (int i = 0; i < fields.length; i++) {
            result[i] = BookCategory.valueOf(fields[i]);
        }

        return result;
    }

    @Override
    public String toString() {
        return "Book [authors=" + authors + ", categories=" + Arrays.toString(categories) + ", id=" + id + ", isbn="
                + isbn + ", price=" + price + ", publisher=" + publisher + ", publisherId=" + publisherId + ", title="
                + title + ", year=" + year + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Book book) {
        if (id == book.id) {
            return 0;
        } else if (id > book.id) {
            return 1;
        } else {
            return -1;
        }
    }

}
