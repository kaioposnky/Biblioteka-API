package org.busnake.biblioteka_api.controller;

import org.springframework.http.ResponseEntity;

public interface GenericController<E> {
    ResponseEntity<?> all();
    ResponseEntity<?> one(Long id);
    ResponseEntity<?> delete(Long id);
}
