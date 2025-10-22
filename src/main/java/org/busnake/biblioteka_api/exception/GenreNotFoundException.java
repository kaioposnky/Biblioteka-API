package org.busnake.biblioteka_api.exception;

public class GenreNotFoundException extends RuntimeException {
  public GenreNotFoundException(Long id) {
    super("Could not find genre with id " + id);
  }
}
