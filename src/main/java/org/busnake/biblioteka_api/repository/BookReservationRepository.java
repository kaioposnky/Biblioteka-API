package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    @Query("SELECT br FROM BookReservation br WHERE br.book.id = :bookId AND " +
           "((br.loanStartDate <= :endDate AND br.dueDate >= :startDate))")
    List<BookReservation> findConflictingReservations(@Param("bookId") Long bookId, 
                                                      @Param("startDate") LocalDate startDate, 
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT br FROM BookReservation br WHERE br.user.id = :userId AND br.dueDate >= CURRENT_DATE")
    List<BookReservation> findActiveReservationsByUserId(@Param("userId") Long userId);
}
