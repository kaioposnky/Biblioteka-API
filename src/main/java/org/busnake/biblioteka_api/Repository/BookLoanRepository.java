package org.busnake.biblioteka_api.Repository;

import org.busnake.biblioteka_api.Model.Entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
}
