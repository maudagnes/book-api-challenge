package services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import models.Book;
import repositories.BookRepository;
import java.util.List;
import play.libs.Json;


@Singleton
public class BookService {

    private final BookRepository bookRepository;

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> fetchAllBooks() {
        return bookRepository.fetchAllBooks();
    }

    public Book createBook(Book book) {
        return bookRepository.createBook(book);
    }
}
