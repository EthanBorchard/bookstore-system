package com.inn.cafe.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@NamedQuery(name = "Book.getAllBooks", query = "select new com.inn.cafe.wrapper.BookWrapper(b.id,b.title,b.author,b.price,b.status,b.category.id,b.category.name) from Book b")

@NamedQuery(name = "Book.updateBookStatus", query = "update Book b set b.status=:status where b.id=:id")

@NamedQuery(name = "Book.getBookByCategory", query = "select new com.inn.cafe.wrapper.BookWrapper(b.id,b.title) from Book b where b.category.id=:id and b.status='true'")

@NamedQuery(name = "Book.getBookById", query = "select new com.inn.cafe.wrapper.BookWrapper(b.id,b.title,b.author,b.price) from Book b where b.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "book")
public class Book implements Serializable {

    @Serial
    public static final long serialVersionUID = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;

}
