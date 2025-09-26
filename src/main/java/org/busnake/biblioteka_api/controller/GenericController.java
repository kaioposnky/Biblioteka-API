package org.busnake.biblioteka_api.controller;

import org.springframework.http.ResponseEntity;

public interface GenericController<E> {
    ResponseEntity<?> all();
    ResponseEntity<?> one(Long id);
    ResponseEntity<?> save(E entity);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> update(E entity, Long id);
}
