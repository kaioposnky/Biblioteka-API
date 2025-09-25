package org.busnake.biblioteka_api.Exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
            super("Could not find user with id " + id);
    }
}
