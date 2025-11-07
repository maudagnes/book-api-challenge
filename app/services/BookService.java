package services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import models.Book;
import models.BookListResponse;
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

    public BookListResponse fetchAllBooks() {
        List<Book> books = bookRepository.fetchAllBooks();
        return new BookListResponse(books);
    }

    public Book createBook(Book book) {
        return bookRepository.createBook(book);
    }
}
