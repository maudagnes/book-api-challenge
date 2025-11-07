package repositories;

import dto.BookDTO;
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
            .createQuery("SELECT b FROM BookDTO b", BookDTO.class)
            .getResultList()
            .stream()
            .map(dto -> {
                Book book = new Book(
                    dto.getIsbn(),
                    dto.getTitle(),
                    dto.getSubtitle(),
                    dto.getCopyrightYear(),
                    dto.getStatus()
                );
                return book;
            })
            .toList();
    }

     /**
     * Save a new book to the database.
     */
    public Book createBook(Book book) {
        return jpaApi.withTransaction("default", em -> {
            BookDTO dto = new BookDTO();
            dto.setIsbn(book.getIsbn());
            dto.setTitle(book.getTitle());
            dto.setSubtitle(book.getSubtitle());
            dto.setCopyrightYear(book.getCopyrightYear());
            dto.setStatus(book.getStatus());
            
            em.persist(dto);
            em.flush();
            
            return new Book(
                dto.getIsbn(),
                dto.getTitle(),
                dto.getSubtitle(),
                dto.getCopyrightYear(),
                dto.getStatus()
            );
        });
    }
}