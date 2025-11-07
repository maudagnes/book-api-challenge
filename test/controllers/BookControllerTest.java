package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Book;
import org.junit.Test;
import play.Application;
import play.data.Form;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import services.BookService;
import controllers.validators.BookValidationException;
import models.BookListResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

public class BookControllerTest extends WithApplication {

    private BookService bookService;

    @Override
    protected Application provideApplication() {
        bookService = mock(BookService.class);
        
        return new GuiceApplicationBuilder()
            .overrides(binder -> {
                binder.bind(BookService.class).toInstance(bookService);
            })
            .build();
    }

    @Test
    public void testGetAllBooks_ShouldReturnEmptyList() {
        when(bookService.fetchAllBooks()).thenReturn(new BookListResponse(new ArrayList<>()));

        Http.RequestBuilder request = new Http.RequestBuilder()
            .method(GET)
            .uri("/books");
        
        Result result = route(app, request);
        
        assertNotNull("Result should not be null", result);

        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().orElse(""));
        
        JsonNode json = Json.parse(contentAsString(result));
        
        assertTrue("JSON response must be an object", json.isObject());

        JsonNode booksArray = json.get("books");

        assertNotNull("The 'books' field must not be null", booksArray);
        assertTrue("The 'books' field must be an array", booksArray.isArray());
        assertEquals(0, booksArray.size());
        
        verify(bookService, times(1)).fetchAllBooks();
    }

    @Test
    public void testGetAllBooks_ShouldReturnListOfBooks() {
        Book book1 = createTestBook("978-0-123456-78-9", "Test Book 1", "2023", "available");
        Book book2 = createTestBook("978-0-987654-32-1", "Test Book 2", "2022", "borrowed");
        List<Book> books = Arrays.asList(book1, book2);
        
        when(bookService.fetchAllBooks()).thenReturn(new BookListResponse(books));

        Http.RequestBuilder request = new Http.RequestBuilder()
            .method(GET)
            .uri("/books");
        
        Result result = route(app, request);
        
        assertNotNull("Result should not be null", result);

        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().orElse(""));
        
        JsonNode json = Json.parse(contentAsString(result));
        
        assertTrue("JSON response must be an object", json.isObject());

        JsonNode booksArray = json.get("books");

        assertNotNull("The 'books' field must not be null", booksArray);
        assertTrue("The 'books' field must be an array", booksArray.isArray());
        
        assertEquals(2, booksArray.size());
        
        JsonNode firstBook = booksArray.get(0);
        assertEquals("978-0-123456-78-9", firstBook.get("isbn").asText());
        assertEquals("Test Book 1", firstBook.get("title").asText());
        
        verify(bookService, times(1)).fetchAllBooks();
    }

    @Test
    public void testCreateBook_ShouldReturnCreatedBook() throws BookValidationException {
        Book inputBook = createTestBook("978-0-123456-78-9", "New Book", "A Subtitle", "2024", "available");
        Book savedBook = createTestBook("978-0-123456-78-9", "New Book", "A Subtitle", "2024", "available");

        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        Http.RequestBuilder request = new Http.RequestBuilder()
            .method(POST)
            .uri("/books")
            .bodyJson(Json.toJson(inputBook))
            .header(Http.HeaderNames.CONTENT_TYPE, "application/json");
        
        Result result = route(app, request);
        
        assertNotNull("Result should not be null", result);

        assertEquals(OK, result.status());
        
        JsonNode json = Json.parse(contentAsString(result));
        assertEquals("A Subtitle", json.get("subtitle").asText());
        
        verify(bookService, times(1)).createBook(any(Book.class));
    }

     @Test
    public void testCreateBook__WithoutSubtitle_ShouldReturnCreatedBook() throws BookValidationException {
        Book inputBook = createTestBook("978-0-123456-78-9", "New Book", "2024", "available");
        Book savedBook = createTestBook("978-0-123456-78-9", "New Book", "2024", "available");
        
        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        Http.RequestBuilder request = new Http.RequestBuilder()
            .method(POST)
            .uri("/books")
            .bodyJson(Json.toJson(inputBook))
            .header(Http.HeaderNames.CONTENT_TYPE, "application/json");
        
        Result result = route(app, request);
        
        assertNotNull("Result should not be null", result);

        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().orElse(""));
        
        JsonNode json = Json.parse(contentAsString(result));
        assertEquals("978-0-123456-78-9", json.get("isbn").asText());
        assertEquals("New Book", json.get("title").asText());
        
        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    public void testCreateBook_MissingTitle_ShouldReturnBadRequest() throws BookValidationException {
        Book invalidBook = createTestBook(null, "978-0-123456-78-9", "", "2024", "available");

        Http.RequestBuilder request = new Http.RequestBuilder()
            .method(POST)
            .uri("/books")
            .bodyJson(Json.toJson(invalidBook))
            .header(Http.HeaderNames.CONTENT_TYPE, "application/json");
        
        Result result = route(app, request);
        
        assertNotNull("Result should not be null", result);

        assertEquals(BAD_REQUEST, result.status());
        assertEquals("application/json", result.contentType().orElse(""));
        
        JsonNode json = Json.parse(contentAsString(result));
        assertEquals("MISSING_REQUIRED_FIELDS", json.get("error").asText());
        assertTrue(json.get("description").asText().contains("required fields"));
    }

    private Book createTestBook(String isbn, String title, String copyrightYear, String status) {
        Book book = new Book(isbn, title, null, copyrightYear, status);
        return book;
    }

    private Book createTestBook(String isbn, String title, String subtitle, String copyrightYear, String status) {
        Book book = new Book(isbn, title, subtitle, copyrightYear, status);
        return book;
    }
}

