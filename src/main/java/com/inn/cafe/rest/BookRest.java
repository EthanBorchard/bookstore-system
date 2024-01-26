package com.inn.cafe.rest;

import com.inn.cafe.wrapper.BookWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/books")
public interface BookRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewBook(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<BookWrapper>> getAllBooks();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateBook(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBook(@PathVariable Integer id);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<BookWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<BookWrapper> getBookById(@PathVariable Integer id);

}
