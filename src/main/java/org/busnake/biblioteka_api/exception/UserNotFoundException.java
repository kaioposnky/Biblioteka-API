package org.busnake.biblioteka_api.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
            super("Could not find user with id " + id);
    }
}
