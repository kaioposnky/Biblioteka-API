package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
