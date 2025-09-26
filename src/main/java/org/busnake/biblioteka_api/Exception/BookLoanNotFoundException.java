package org.busnake.biblioteka_api.Exception;

public class BookLoanNotFoundException extends EntityNotFoundException {
    public BookLoanNotFoundException(Long id) {
        super("Could not find book loan with id " + id);
    }
}
