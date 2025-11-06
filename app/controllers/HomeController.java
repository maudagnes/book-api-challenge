package controllers;

import play.mvc.*;
import play.libs.Json;
import java.util.Map;

public class HomeController extends Controller {

    public Result index() {
        return ok(Json.toJson(Map.of(
            "message", "Book API",
            "version", "1.0",
            "endpoints", Map.of(
                "GET /books", "Retrieve all books",
                "POST /books", "Create a new book"
            )
        )));
    }

}