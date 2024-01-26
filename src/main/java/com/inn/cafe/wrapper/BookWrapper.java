package com.inn.cafe.wrapper;

import lombok.Data;

@Data
public class BookWrapper {

    Integer id;

    String title;

    String author;

    Integer price;

    String status;

    Integer categoryId;

    String categoryName;

    public BookWrapper() {

    }

    public BookWrapper(Integer id, String title, String author, Integer price, String status,
                       Integer categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public BookWrapper(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public BookWrapper(Integer id, String title, String author, Integer price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

}
