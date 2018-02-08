package com.bookfinder.model;

/**
 * Created by jdzido on 07.02.2018.
 */
public class Book {

    private String title;
    private String isbn;
    private String publisher;
    private String cover;

    public Book(String title, String isbn, String publisher, String cover) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
