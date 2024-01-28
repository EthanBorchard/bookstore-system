package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Book;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.constants.BookstoreConstants;
import com.inn.cafe.dao.BookDao;
import com.inn.cafe.service.BookService;
import com.inn.cafe.utils.BookstoreUtils;
import com.inn.cafe.wrapper.BookWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewBook(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateBookMap(requestMap, false)) {
                    bookDao.save(getBookFromMap(requestMap, false));
                    return BookstoreUtils.getResponseEntity("Book Added Successfully", HttpStatus.OK);
                }
                return BookstoreUtils.getResponseEntity(BookstoreConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return BookstoreUtils.getResponseEntity(BookstoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return BookstoreUtils.getResponseEntity(BookstoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateBookMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("title")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Book getBookFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Book book = new Book();
        if (isAdd) {
            book.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            book.setStatus("true");
        }
        book.setCategory(category);
        book.setTitle(requestMap.get("title"));
        book.setAuthor(requestMap.get("author"));
        book.setPrice(Integer.parseInt(requestMap.get("price")));
        return book;
    }

    @Override
    public ResponseEntity<List<BookWrapper>> getAllBooks() {
        try {
            return new ResponseEntity<>(bookDao.getAllBooks(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBook(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateBookMap(requestMap, true)) {
                    Optional<Book> optional = bookDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Book book = getBookFromMap(requestMap, true);
                        book.setStatus(optional.get().getStatus());
                        bookDao.save(book);
                        return BookstoreUtils.getResponseEntity("Book Updated Successfully", HttpStatus.OK);
                    } else {
                        return BookstoreUtils.getResponseEntity("Book ID does not exist", HttpStatus.OK);
                    }
                } else {
                    return BookstoreUtils.getResponseEntity(BookstoreConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return BookstoreUtils.getResponseEntity(BookstoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return BookstoreUtils.getResponseEntity(BookstoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBook(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = bookDao.findById(id);
                if (!optional.isEmpty()) {
                    bookDao.deleteById(id);
                    return BookstoreUtils.getResponseEntity("Book Removed Successfully", HttpStatus.OK);
                } else {
                    return BookstoreUtils.getResponseEntity("Book ID does not exist", HttpStatus.OK);
                }
            } else {
                return BookstoreUtils.getResponseEntity(BookstoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return BookstoreUtils.getResponseEntity(BookstoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = bookDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    bookDao.updateBookStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return BookstoreUtils.getResponseEntity("Book Status Updated Successfully", HttpStatus.OK);
                } else {
                    return BookstoreUtils.getResponseEntity("Book ID does not exist", HttpStatus.OK);
                }
            } else {
                return BookstoreUtils.getResponseEntity(BookstoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return BookstoreUtils.getResponseEntity(BookstoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<BookWrapper>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(bookDao.getBookByCategory(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BookWrapper> getBookById(Integer id) {
        try {
            return new ResponseEntity<>(bookDao.getBookById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new BookWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
