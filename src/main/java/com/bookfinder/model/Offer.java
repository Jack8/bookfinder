package com.bookfinder.model;

/**
 * Created by jdzido on 08.02.2018.
 */
public class Offer {

    private String isbn;
    private String link;
    private String currency;
    private Double price;

    public Offer(String isbn, String link, String currency, Double price) {
        this.isbn = isbn;
        this.link = link;
        this.currency = currency;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
