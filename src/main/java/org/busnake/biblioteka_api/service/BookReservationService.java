package org.busnake.biblioteka_api.service;

import org.busnake.biblioteka_api.exception.BookNotFoundException;
import org.busnake.biblioteka_api.exception.UserNotFoundException;
import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.BookReservation;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.repository.BookReservationRepository;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookReservationService {

    private final BookReservationRepository bookReservationRepository;
    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookReservationService(BookReservationRepository bookReservationRepository,
                                 BookLoanRepository bookLoanRepository,
                                 BookRepository bookRepository,
                                 UserRepository userRepository) {
        this.bookReservationRepository = bookReservationRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public BookReservation createReservation(Long bookId, Long userId, LocalDate loanStartDate, LocalDate dueDate) 
            throws BookNotFoundException, UserNotFoundException, IllegalStateException {

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(bookId)
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        // Reservas ativas do usuario
        List<BookReservation> activeReservations = bookReservationRepository.findActiveReservationsByUserId(userId);
        if (!activeReservations.isEmpty()) {
            throw new IllegalStateException("Usuário já possui uma reserva ativa");
        }

        // Reservas do livro que podem estar em conflito
        List<BookReservation> conflictingReservations = bookReservationRepository
                .findConflictingReservations(bookId, loanStartDate, dueDate);
        if (!conflictingReservations.isEmpty()) {
            throw new IllegalStateException("Já existe uma reserva para este livro no período solicitado");
        }

        List<BookLoan> activeLoans = bookLoanRepository.findBookLoansByUserId(userId).stream()
                .filter(loan -> !loan.getIsReturned() && 
                       loan.getLoanDate().isBefore(dueDate.plusDays(1)) && 
                       loan.getDueDate().isAfter(loanStartDate.minusDays(1)))
                .toList();

        if (!activeLoans.isEmpty()) {
            throw new IllegalStateException("Existe um empréstimo ativo que conflita com o período da reserva");
        }

        BookReservation bookReservation = new BookReservation();
        bookReservation.setBook(book);
        bookReservation.setUser(user);
        bookReservation.setLoanStartDate(loanStartDate);
        bookReservation.setDueDate(dueDate);

        return bookReservationRepository.save(bookReservation);
    }
}
