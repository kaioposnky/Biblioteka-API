package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
}
