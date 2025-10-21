package org.busnake.biblioteka_api.advices;

import org.busnake.biblioteka_api.exception.InvalidAuthCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;

@RestControllerAdvice
public class InvalidAuthCredentialsAdvice {

    @ExceptionHandler(InvalidAuthCredentials.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> invalidAuthCredentialsHandler(InvalidAuthCredentials ex){
        return createErrorResponse("Credenciais inválidas!", HttpStatus.UNAUTHORIZED);
    }
}
