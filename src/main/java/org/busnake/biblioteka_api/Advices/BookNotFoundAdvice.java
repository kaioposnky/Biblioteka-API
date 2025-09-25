package org.busnake.biblioteka_api.Advices;

import org.busnake.biblioteka_api.Exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookNotFoundAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookNotFoundHandler(BookNotFoundException ex){
        return ex.getMessage();
    }
}
