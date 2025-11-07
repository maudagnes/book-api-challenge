package controllers.validators;

import models.Book;
import play.data.Form;
import jakarta.inject.Singleton;
import controllers.validators.BookValidationException;
import controllers.validators.BookValidationError;

@Singleton
public class BookValidator {
  
    public Book isValid(Form<Book> form) throws BookValidationException {
        if (form == null) {
            throw new BookValidationException(BookValidationError.INVALID_REQUEST);
        }
        
        if (form.hasErrors()) {
            throw new BookValidationException(BookValidationError.INVALID_REQUEST);
        }
        
        Book book = form.get();
        if (book == null) {
            throw new BookValidationException(BookValidationError.INVALID_BODY);
        }
        
        if (!hasAllRequiredFields(book)) {
            throw new BookValidationException(BookValidationError.MISSING_REQUIRED_FIELDS);
        }
        
        return book;
    }
    
    public boolean hasAllRequiredFields(Book book) {
        return book.getIsbn() != null && !book.getIsbn().trim().isEmpty()
            && book.getTitle() != null && !book.getTitle().trim().isEmpty()
            && book.getCopyrightYear() != null && !book.getCopyrightYear().trim().isEmpty()
            && book.getStatus() != null && !book.getStatus().trim().isEmpty();
    }
}
