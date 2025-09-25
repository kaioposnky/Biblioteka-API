package org.busnake.biblioteka_api.Repository;

import org.busnake.biblioteka_api.Model.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
