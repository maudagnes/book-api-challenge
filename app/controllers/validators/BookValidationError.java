package controllers.validators;

public enum BookValidationError {
    INVALID_REQUEST("Invalid request"),
    INVALID_BODY("Body does not correspond to a valid Book"),
    MISSING_REQUIRED_FIELDS("All required fields (isbn, title, copyrightYear, status) must be present and non-empty");
    
    private final String description;
    
    BookValidationError(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}

