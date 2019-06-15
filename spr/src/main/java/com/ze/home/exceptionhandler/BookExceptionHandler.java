package com.ze.home.exceptionhandler;

import com.ze.home.book.Book;
import com.ze.home.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BookExceptionHandler {

    @ExceptionHandler(BookException.class)
    public ResponseEntity<Book> handlle(BookException e){
        log.info("BookExceptionHandler.handlle {}", e.getMessage());
        return new ResponseEntity<>(new Book("name", "001"), HttpStatus.FORBIDDEN);
    }
}
