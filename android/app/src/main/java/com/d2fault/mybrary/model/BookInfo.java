package com.d2fault.mybrary.model;

import java.util.ArrayList;

public class BookInfo {
    private ArrayList<String> authors, translators;
    private String contents, datetime, isbn, publisher, status, title, url, thumbnail;
    private int price, sale_price;

    public BookInfo(ArrayList<String> authors, ArrayList<String> translators
            , String contents, String datetime, String isbn, String publisher, String status
            , String title, String url, String thumbnail, int price, int sale_price) {
        this.authors = authors;
        this.translators = translators;
        this.contents = contents;
        this.datetime = datetime;
        this.isbn = isbn;
        this.publisher = publisher;
        this.status = status;
        this.title = title;
        this.url = url;
        this.thumbnail = thumbnail;
        this.price = price;
        this.sale_price = sale_price;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }
}
