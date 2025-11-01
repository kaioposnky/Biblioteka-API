package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:isAvailable IS NULL OR b.isAvailable = :isAvailable)")
    List<Book> findBooksByTitleAndAvailability(@Param("title") String title,
                                               @Param("isAvailable") Boolean isAvailable);
}
