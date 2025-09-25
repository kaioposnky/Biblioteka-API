package org.busnake.biblioteka_api.Controller;

import org.springframework.http.ResponseEntity;

public interface RESTfulController<E> {
    ResponseEntity<?> all();
    ResponseEntity<?> one(Long id);
    ResponseEntity<?> save(E entity);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> update(E entity, Long id);
}
