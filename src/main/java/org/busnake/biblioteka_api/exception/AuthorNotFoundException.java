package org.busnake.biblioteka_api.exception;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(Long id) {
        super("Could not find author with id " + id);
    }
}
