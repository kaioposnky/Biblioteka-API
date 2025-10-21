package org.busnake.biblioteka_api.advices;

import org.busnake.biblioteka_api.exception.InvalidAuthCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidAuthCredentialsAdvice {

    @ExceptionHandler(InvalidAuthCredentials.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalidAuthCredentialsHandler(InvalidAuthCredentials ex){
        return "Email ou senha incorretos!";
    }
}
