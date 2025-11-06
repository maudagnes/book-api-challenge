package repositories;

import models.Book;
import play.db.jpa.JPAApi;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class BookRepository {

    private final JPAApi jpaApi;

    @Inject
    private BookRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    /**
     * Retrieve all books from database.
     */
    public List<Book> fetchAllBooks() {
        return jpaApi.em("default")
            .createQuery("SELECT b FROM Book b", Book.class)
            .getResultList();
    }

     /**
     * Save a new book to the database.
     */
    public Book createBook(Book book) {
        return jpaApi.withTransaction("default", em -> {
            em.persist(book);
            return book;
        });
    }
}