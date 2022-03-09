package com.bookstore.app;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import com.bookstore.dao.BookDao;
import com.bookstore.dao.DaoFactory;
import com.bookstore.dao.DaoFactoryCreator;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.model.BookCategory;
import com.bookstore.model.Publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewBookStore {
    private static final Logger log = LoggerFactory.getLogger(NewBookStore.class);

    public static void main(String[] args) throws Exception {
        bookstoreTryPublisherUsingJpa();
    }

    public void printBooksUsingJpa() throws Exception {
        BookDao bookDao = DaoFactoryCreator.getDaoFactory().getBookDao();
        for (Book book : bookDao.findAll()) {
            System.out.println(book);
        }
    }

    public void addBookUsingJpa(Book book) {
        BookDao bookDao = DaoFactoryCreator.getDaoFactory().getBookDao();
        bookDao.saveOrUpdate(book);
    }

    private static void askWithTitle(Scanner scan) {
        System.out.print("Find book by title: ");
        String title = scan.nextLine();
        List<Book> result = DaoFactoryCreator.getDaoFactory().getBookDao().findByTitle(title);
        System.out.println(result);
    }

    private static void askWithAuthor(Scanner scan) {
        System.out.print("Find book by author: ");
        Long authorId = scan.nextLong();
        scan.nextLine();
        Optional<Author> author = DaoFactoryCreator.getDaoFactory().getAuthorDao().findById(authorId);
        if (author.isPresent()) {
            List<Book> result = DaoFactoryCreator.getDaoFactory().getBookDao().findByAuthor(author.get());
            System.out.println(result);
        } else {
            System.out.printf("Author with id %d doesn't exist!\n", authorId);
        }
    }

    private static void bookstoreTryPublisherUsingJpa() throws Exception {
        try (DaoFactory daoFactory = DaoFactoryCreator.getDaoFactory();
                Scanner scan = new Scanner(System.in)) {
            NewBookStore app = new NewBookStore();
            app.printBooksUsingJpa();

            scan.useLocale(Locale.ITALY);
            System.out.print("Do you want to insert a book? (y/n)");
            String again = scan.nextLine();
            while ("y".equals(again.toLowerCase())) {
                Book book = inputBook(scan);
                app.addBookUsingJpa(book);
                log.info("Added a book with id {}", book.getId());
                System.out.print("Again? (y/n)");
                again = scan.nextLine();
            }
            app.printBooksUsingJpa();

            askWithTitle(scan);

            askWithAuthor(scan);

            System.out.println("Bye Bye!");
        }
    }

    private static Book inputBook(Scanner scan) {
        System.out.print("Id: ");
        long id = scan.nextLong();
        scan.nextLine();
        System.out.print("Title: ");
        String title = scan.nextLine();
        Author[] authors = inputAuthors(scan);
        System.out.print("Price: ");
        BigDecimal price = scan.nextBigDecimal();
        scan.nextLine();
        Publisher publisher = inputPublisher(scan);
        BookCategory[] categories = inputCategories(scan);
        Book book = new Book(id, title, price, authors, publisher);
        for (BookCategory category : categories) {
            book.addCategory(category);
        }
        return book;
    }

    private static Author[] inputAuthors(Scanner scan) {
        Author[] authors = {};
        System.out.println("Authors:");
        String again = "y";
        while ("y".equals(again.toLowerCase())) {
            Author author = inputAuthor(scan);
            authors = Arrays.copyOf(authors, authors.length + 1);
            authors[authors.length - 1] = author;
            System.out.print("Another author? (y/n)");
            again = scan.nextLine();
        }
        return authors;
    }

    private static Author inputAuthor(Scanner scan) {
        System.out.println("  Author: ");
        System.out.print("    Id: ");
        long id = scan.nextLong();
        scan.nextLine();
        System.out.print("    First Name: ");
        String firstName = scan.nextLine();
        System.out.print("    Last Name: ");
        String lastName = scan.nextLine();
        return new Author(id, firstName, lastName);
    }

    private static Publisher inputPublisher(Scanner scan) {
        System.out.println("Publisher: ");
        System.out.print("  Id: ");
        long id = scan.nextLong();
        scan.nextLine();
        System.out.print("  Name: ");
        String name = scan.nextLine();
        return new Publisher(id, name);
    }

    private static BookCategory[] inputCategories(Scanner scan) {
        BookCategory[] categories = {};
        System.out.println("Categories:");
        Optional<BookCategory> category = null;
        do {
            category = inputCategory(scan);
            if (category.isPresent()) {
                categories = Arrays.copyOf(categories, categories.length + 1);
                categories[categories.length - 1] = category.get();
            }
        } while (category.isPresent());
        return categories;
    }

    private static Optional<BookCategory> inputCategory(Scanner scan) {
        BookCategory[] categories = BookCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("  %d. %s\n", i + 1, categories[i].toString());
        }
        System.out.println("  X. Fine aggiunta categorie");
        System.out.print("Scegli una categoria da aggiungere: ");
        String category = scan.nextLine();
        if ("X".equals(category.toUpperCase())) {
            return Optional.empty();
        } else {
            return Optional.of(categories[Integer.parseInt(category) - 1]);
        }
    }

}
