package com.bookstore.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

import com.bookstore.dao.AuthorDao;
import com.bookstore.dao.BookDao;
import com.bookstore.dao.DaoFactory;
import com.bookstore.dao.DaoFactoryCreator;
import com.bookstore.dao.PublisherDao;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.model.BookCategory;
import com.bookstore.model.BookIsbnComparator;
import com.bookstore.model.BookTitleComparator;
import com.bookstore.model.Publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookStore {
    private static final Logger log = LoggerFactory.getLogger(BookStore.class);
    public Collection<Book> books = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // originalBookstoreApp();

        // try (DaoFactory daoFactory = DaoFactoryCreator.getDaoFactory()) {
        //     PublisherDao publisherDao = daoFactory.getPublisherDao();

        //     System.out.println("List of authors: ");
        //     List<Publisher> publishers = publisherDao.findAll();
        //     for (Publisher publisher : publishers) {
        //         System.out.println(publisher);
        //     }

        //     System.out.println("Find publisher by id: ");
        //     Optional<Publisher> existentPublisher = publisherDao.findById(1);
        //     existentPublisher.ifPresentOrElse(p -> System.out.println(p), () -> System.out.println("Not fuond by id 1"));
        //     Optional<Publisher> publisherWithId = publisherDao.findById(100);
        //     publisherWithId.ifPresentOrElse(p -> System.out.println(p), () -> System.out.println("Not fuond by id 100"));

        //     System.out.println("Add a newPublisher (generating id): ");
        //     Publisher newPublisher = publisherDao.saveOrUpdate(new Publisher(null, "Name"));
        //     System.out.println(newPublisher);

        //     System.out.println("newPublisher with name updated: ");
        //     newPublisher.setName("UpdatedName");
        //     Publisher updatedNewPublisher = publisherDao.saveOrUpdate(newPublisher);
        //     System.out.println(updatedNewPublisher);

        //     System.out.println("Add a new publisher (specific id): ");
        //     Publisher anotherNewPublisher = publisherDao.saveOrUpdate(new Publisher(100L, "Another"));
        //     System.out.println(anotherNewPublisher);
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // try (DaoFactory daoFactory = DaoFactoryCreator.getDaoFactory()) {
        //     AuthorDao authorDao = daoFactory.getAuthorDao();

        //     System.out.println("List of authors: ");
        //     List<Author> authors = authorDao.findAll();
        //     for (Author author : authors) {
        //         System.out.println(author);
        //     }

        //     System.out.println("Find author by id: ");
        //     Optional<Author> existentAuthor = authorDao.findById(2);
        //     existentAuthor.ifPresentOrElse(p -> System.out.println(p), () -> System.out.println("Not fuond by id 2"));
        //     Optional<Author> authorWithId = authorDao.findById(100);
        //     authorWithId.ifPresentOrElse(p -> System.out.println(p), () -> System.out.println("Not fuond by id 100"));

        //     System.out.println("Add a newAuthor (generating id): ");
        //     Author newAuthor = authorDao.saveOrUpdate(new Author(null, "Name", "Surname"));
        //     System.out.println(newAuthor);

        //     System.out.println("newAuthor with first_name and last_name updated: ");
        //     newAuthor.setFirstName("UpdatedName");
        //     newAuthor.setLastName("UpdatedSurname");
        //     Author updatedNewAuthor = authorDao.saveOrUpdate(newAuthor);
        //     System.out.println(updatedNewAuthor);

        //     System.out.println("Add a new author (specific id): ");
        //     Author annotherNewAuthor = authorDao.saveOrUpdate(new Author(100L, "Another", "Another"));
        //     System.out.println(annotherNewAuthor);
        // }

        // try (DaoFactory daoFactory = DaoFactoryCreator.getDaoFactory()) {
        //     BookDao bookDao = daoFactory.getBookDao();

        //     System.out.println("List of books: ");
        //     List<Book> books = bookDao.findAll();
        //     for (Book book : books) {
        //         System.out.println(book);
        //     }

        //     System.out.println("Find book by id: ");
        //     Optional<Book> existentBook = bookDao.findById(2);
        //     existentBook.ifPresentOrElse(b -> System.out.println(b), () -> System.out.println("Not found by id 2"));
        //     Optional<Book> notExistentBook = bookDao.findById(100);
        //     notExistentBook.ifPresentOrElse(b -> System.out.println(b), () -> System.out.println("Not found by id 100"));

        //     System.out.println("Find books by title: ");
        //     List<Book> searchedTitle = bookDao.findByTitle("Libro 1");
        //     for (Book book : searchedTitle) {
        //         System.out.println(book);
        //     }

        //     System.out.println("Find books by author: ");
        //     Author a = new Author(1L, "Mario", "Rossi");
        //     List<Book> searchedAuthor = bookDao.findByAuthor(a);
        //     for (Book book : searchedAuthor) {
        //         System.out.println(book);
        //     }

        //     System.out.println("Add a newBook (generating id): ");
        //     Book newBook = bookDao.saveOrUpdate(new Book(null, "Title",
        //             BigDecimal.valueOf(10.50), 6354260L, 2012, 1L));
        //     System.out.println(newBook);

        //     System.out.println("newBook with data updated: ");
        //     newBook.setTitle("UpdatedTitle");
        //     newBook.setYear(2013);
        //     Book updatedNewBook = bookDao.saveOrUpdate(newBook);
        //     System.out.println(updatedNewBook);

        //     System.out.println("Add a new book (specific id): ");
        //     Book anotherNewBook = bookDao.saveOrUpdate(new Book(100L, "Title1",
        //             BigDecimal.valueOf(11.50), 6359260L, 2008, 2L));
        //     System.out.println(anotherNewBook);
        // }
    }

    public void addBook(Book book) {
        log.debug("Adding book with id {}", book.getId());
        this.books.add(book);
    }

    public void removeBook(Book book) {
        java.util.Iterator<Book> itr = this.books.iterator();

        while (itr.hasNext()) {
            Book bookN = itr.next();
            if (bookN.getId() == book.getId()) {
                itr.remove();
            }
        }
    }

    public void printBooks() {
        List<Book> sortedList = new ArrayList<>(this.books);

        // sortedList.sort(new BookIsbnComparator());

        // sortedList.sort(new BookTitleComparator());

        for (Book bookN : sortedList) {
            System.out.println(bookN);
        }
    }

    public Book[] searchBooksByTitle(String title) {
        List<Book> searchedBooks = new ArrayList<>();
        java.util.Iterator<Book> itr = this.books.iterator();

        while (itr.hasNext()) {
            Book bookN = itr.next();
            if (bookN.getTitle().equals(title)) {
                searchedBooks.add(bookN);
            }
        }

        return searchedBooks.toArray(new Book[] {});
    }

    public Book[] searchBooksByAuthor(Author author) {
        List<Book> searchedBooks = new ArrayList<>();
        java.util.Iterator<Book> itr = this.books.iterator();

        while (itr.hasNext()) {
            Book bookN = itr.next();
            for (Author authorN : bookN.getAuthors()) {
                if (authorN.hashCode() == author.hashCode() && authorN.equals(author)) {
                    searchedBooks.add(bookN);
                }
            }
        }

        return searchedBooks.toArray(new Book[] {});
    }

    public void loadArchive() {
        String home = System.getProperty("user.home");
        File homeDir = new File(home);
        File bookstoreDir = new File(homeDir, ".bookstore");

        if (bookstoreDir.exists() && bookstoreDir.isDirectory()) {
            Properties properties = new Properties();
            File bookstoreConf = new File(bookstoreDir, "bookstore.conf");
            if (bookstoreConf.exists() && bookstoreConf.isFile()) {
                try (InputStream is = new FileInputStream(bookstoreConf)) {
                    properties.load(is);
                    log.info("Loading configuration properties from {}", bookstoreConf.getAbsolutePath());
                    log.info("bookstore.archive.name={}", properties.getProperty("bookstore.archive.name"));
                    log.info("bookstore.archive.directory={}\n", properties.getProperty("bookstore.archive.directory"));
                } catch (IOException e) {
                    log.error("Error loading configuraion file ({})", bookstoreConf.getAbsolutePath());
                }
            }
        }

        if (bookstoreDir.exists() && bookstoreDir.isDirectory()) {
            File archive = new File(bookstoreDir, "books.csv");
            if (archive.exists() && archive.isFile()) {
                log.info("Loading books from {}", archive.getAbsolutePath());
                try (InputStream is = new FileInputStream(archive)) {
                    readBooks(is);
                } catch (IOException e) {
                    log.error("Error reading books archive ({})", archive.getAbsolutePath());
                }
            }
        }
    }

    public void storeArchive() {
        String home = System.getProperty("user.home");
        File homeDir = new File(home);
        File bookstoreDir = new File(homeDir, ".bookstore");

        if (!bookstoreDir.exists()) {
            bookstoreDir.mkdirs();
        }

        if (bookstoreDir.exists() && bookstoreDir.isDirectory()) {
            File archive = new File(bookstoreDir, "books.csv");
            log.info("Storing books to {}", archive.getAbsolutePath());
            try (FileOutputStream os = new FileOutputStream(archive)) {
                writeBooks(os);
            } catch (IOException e) {
                log.error("Error writing books archive ({})", archive.getAbsolutePath());
            }
        }
    }

    public void readBooks(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.lines().forEach(line -> addBook(Book.fromCsv(line, ",")));
    }

    public void writeBooks(OutputStream os) {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
        for (Book bookN : books) {
            writer.println(bookN.toCsv(","));
        }
        writer.flush();
    }

    public static void originalBookstoreApp() {
        // BookStore bs = new BookStore();

        // bs.loadArchive();
        // bs.printBooks();

        // insertBook(bs);

        // bs.printBooks();
        // bs.storeArchive();

        // Author author1 = new Author(1L, "Mario", "Rossi");
        // Author author2 = new Author(2L, "Giovanna", "Bianchi");
        // Author author3 = new Author(3L, "Carlo", "Verdi");
        // Publisher publisher1 = new Publisher(1L, "A Publisher");
        // Book book1 = new Book(1L, "Libro 1", new BigDecimal("10.50"), new Author[] { author1, author2 }, publisher1);
        // Book book2 = new Book(2L, "Libro 2", new BigDecimal("5.25"), new Author[] { author3 }, publisher1);
        // Book book3 = new Book(3L, "Another", new BigDecimal("4.25"), new Author[] { author2 }, publisher1);

        // book1.setIsbn(2345678L);
        // book2.setIsbn(1234567L);
        // book3.setIsbn(3456789L);

        // bs.addBook(book1);
        // bs.addBook(book2);
        // bs.addBook(book3);
        // bs.printBooks();

        // bs.removeBook(book1);
        // bs.printBooks();

        // String title = "Libro 1";
        // for (Book bookN : bs.searchBooksByTitle(title)) {
        // System.out.println(bookN);
        // }

        // Author author = new Author(2L, "Giovanna", "Bianchi");
        // for (Book bookN : bs.searchBooksByAuthor(author)) {
        // System.out.println(bookN);
        // }
    }

    public static void insertBook(BookStore bookstore) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many books do you want to insert?");
        int nBooks = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nBooks; i++) {
            System.out.println("Book's data insert > Id: ");
            long Id = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Title: ");
            String title = scanner.nextLine();
            System.out.println("Price: ");
            BigDecimal price = new BigDecimal(scanner.nextLine());

            Book book = new Book(Id, title, price, insertAuthor(scanner), insertPublisher(scanner));
            insertCategory(scanner, book);

            bookstore.addBook(book);
        }

        newBook(scanner, bookstore);
    }

    public static Author[] insertAuthor(Scanner scanner) {
        System.out.println("Author's data insert > How many authors do you want to insert?");
        int nAuthors = scanner.nextInt();
        scanner.nextLine();

        Author[] authors = new Author[nAuthors];

        for (int i = 0; i < nAuthors; i++) {
            System.out.println("Author's id: ");
            long id = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Author's first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Author's last name: ");
            String lastName = scanner.nextLine();

            authors[i] = new Author(id, firstName, lastName);
        }

        return authors;
    }

    public static Publisher insertPublisher(Scanner scanner) {
        System.out.println("Publisher's data insert > Publisher's id: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Publisher's name: ");
        String name = scanner.nextLine();

        return new Publisher(id, name);
    }

    public static void insertCategory(Scanner scanner, Book book) {
        System.out.println("Book's category insert > Choose one of the following categories: ");
        BookCategory[] categories = BookCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println(i + 1 + ". " + categories[i]);
        }
        System.out.println("X. Exit from category selection");

        String answer = scanner.nextLine();

        if (!answer.toLowerCase().equals("x")) {
            for (BookCategory bookCategory : categories) {
                if (Integer.parseInt(answer) - 1 == bookCategory.ordinal()) {
                    book.addCategory(bookCategory);
                }
            }

            insertCategory(scanner, book);
        }
    }

    public static void newBook(Scanner scanner, BookStore bookStore) {
        System.out.println("Again? (y/n) ");
        String answer = scanner.nextLine();

        if (answer.equals("y")) {
            insertBook(bookStore);
        } else if (answer.equals("n")) {
            System.out.println("Bye bye!");
            scanner.close();
        }
    }

}
