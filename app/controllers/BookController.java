package controllers;

import models.Book;
import services.BookService;
import controllers.validators.BookValidator;
import controllers.validators.BookValidationException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.Map;
import play.data.Form;
import play.data.FormFactory;


public class BookController extends Controller {

    private final BookService bookService;
    private final BookValidator bookValidator;
    private final FormFactory formFactory;

    @Inject
    public BookController(BookService bookService, BookValidator bookValidator, FormFactory formFactory) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.formFactory = formFactory;
    }

    /**
     * GET /books - Retrieve all books from database
     */
    public Result getAllBooks() {
        return ok(Json.toJson(bookService.fetchAllBooks()));
    }

    /**
     * POST /books - Create a new book
     */
    public Result createBook(Http.Request request) {
        try {
            Form<Book> form = formFactory.form(Book.class).bindFromRequest(request);            
            Book book = bookValidator.isValid(form);            
            Book savedBook = bookService.createBook(book);

            return ok(Json.toJson(savedBook));
        } catch (BookValidationException e) {
            return badRequest(Json.toJson(e.getError()));
        } catch (Exception e) {
            return badRequest(Json.toJson(Map.of("error", "Invalid book data: " + e.getMessage())));
        }
    }
}