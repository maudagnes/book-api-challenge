package controllers.validators;

import java.util.Map;

public class BookValidationException extends Exception {
    
    private final BookValidationError error;
    
    public BookValidationException(BookValidationError error) {
        super(error.getDescription());
        this.error = error;
    }    
    
    public BookError getError() {
        return new BookError(error.name(), error.getDescription());
    }
}
