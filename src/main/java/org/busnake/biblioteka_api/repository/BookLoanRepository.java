package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.LoanFine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    Collection<BookLoan> findBookLoansByUserId(Long userId);

    List<BookLoan> findAllByDueDateBeforeAndReturnDateIsNullAndLoanFineIsNull(LocalDate dueDateBefore);
}
