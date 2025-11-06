package controllers;

import models.Book;
import services.BookService;
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
    private final FormFactory formFactory;

    @Inject
    public BookController(BookService bookService, FormFactory formFactory) {
        this.bookService = bookService;
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
            if (form.hasErrors()) {
                return badRequest(Json.toJson(Map.of("error", "Invalid book data", "details", form.errorsAsJson())));
            }
            Book book = form.get();

            Book savedBook = bookService.createBook(book);
            return ok(Json.toJson(savedBook));
        } catch (Exception e) {
            return badRequest(Json.toJson(Map.of("error", "Invalid book data: " + e.getMessage())));
        }
    }
}