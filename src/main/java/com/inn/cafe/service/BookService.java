package com.inn.cafe.service;

import com.inn.cafe.wrapper.BookWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BookService {

    ResponseEntity<String> addNewBook(Map<String, String> requestMap);

    ResponseEntity<List<BookWrapper>> getAllBooks();

    ResponseEntity<String> updateBook(Map<String, String> requestMap);

    ResponseEntity<String> deleteBook(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<BookWrapper>> getByCategory(Integer id);

    ResponseEntity<BookWrapper> getBookById(Integer id);
}
