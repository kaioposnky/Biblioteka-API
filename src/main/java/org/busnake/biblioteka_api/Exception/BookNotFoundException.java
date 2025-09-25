package org.busnake.biblioteka_api.Exception;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Long id) {
        super("Could not find book with id " + id);
    }
}
