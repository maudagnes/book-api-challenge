package models;

public class Book {
    private String isbn;
    private String title;
    private String subtitle;
    private String copyrightYear;
    private String status;

    public Book() {
    }

    public Book(String isbn, String title, String subtitle, String copyrightYear, String status) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.copyrightYear = copyrightYear;
        this.status = status;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public String getStatus() {
        return status;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
