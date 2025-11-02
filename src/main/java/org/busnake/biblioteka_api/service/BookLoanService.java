package org.busnake.biblioteka_api.service;

import org.busnake.biblioteka_api.exception.BookLoanNotFoundException;
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
import java.util.Objects;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookReservationRepository bookReservationRepository;

    public BookLoanService(BookLoanRepository bookLoanRepository, UserRepository userRepository, BookRepository bookRepository, BookReservationRepository bookReservationRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookReservationRepository = bookReservationRepository;
    }

    public BookLoan saveBookLoan(Long userId, Long bookId, LocalDate dueDate) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(bookId)
        );

        if(!book.getIsAvailable()){
            return null;
        }

        // checar se tem reservas no periodo
        LocalDate loanStartDate = LocalDate.now();
        List<BookReservation> conflictingReservations = bookReservationRepository
                .findConflictingReservations(bookId, loanStartDate, dueDate);

        // Se existe tiver conflito cancela
        if (!conflictingReservations.isEmpty()) {
            return null;
        }

        BookLoan bookLoan = new BookLoan();
        bookLoan.setUser(user);
        bookLoan.setBook(book);
        bookLoan.setDueDate(dueDate);
        bookLoan.setIsReturned(false);
        bookLoan.setLoanDate(LocalDate.now());

        // Atualiza o livro deixando ele indisponível
        bookRepository.findById(book.getId())
                .map(updatedBook -> {
                    updatedBook.setIsAvailable(false);
                    return bookRepository.save(updatedBook);
                });

        return bookLoanRepository.save(bookLoan);
    }

    public BookLoan renewBookLoan(Long bookLoanId, LocalDate newDueDate, Long userId) {

        // Buscar o empréstimo
        BookLoan bookLoan = bookLoanRepository.findById(bookLoanId).orElseThrow(
                () -> new BookLoanNotFoundException(bookLoanId)
        );

        // Checa se o mesmo usuário que fez o empréstimo é o que fez o request
        if(!Objects.equals(bookLoan.getUser().getId(), userId)){
            throw new IllegalStateException("Usuário não autorizado.");
        }

        // Verificar se o empréstimo já foi devolvido
        if (bookLoan.getIsReturned()) {
            throw new IllegalStateException("Não é possível renovar um empréstimo já devolvido");
        }

        // Verificar se a nova data é posterior à data atual de vencimento
        if (newDueDate.isBefore(bookLoan.getDueDate()) || newDueDate.isBefore(LocalDate.now())) {
            throw new IllegalStateException("A nova data de vencimento deve ser posterior à data atual de vencimento e à data atual");
        }

        // Verificar conflitos com reservas no novo período
        // Período a verificar: da data atual de vencimento até a nova data de vencimento
        LocalDate checkStartDate = bookLoan.getDueDate().plusDays(1);
        List<BookReservation> conflictingReservations = bookReservationRepository
                .findConflictingReservations(bookLoan.getBook().getId(), checkStartDate, newDueDate);

        if (!conflictingReservations.isEmpty()) {
            throw new IllegalStateException("Existe uma reserva no período solicitado para renovação");
        }

        // Se passou em todas as validações, atualizar a data de vencimento
        bookLoan.setDueDate(newDueDate);

        return bookLoanRepository.save(bookLoan);
    }

    public void generateBookLoansFromReservations(List<BookReservation> bookReservations){
        for(BookReservation bookReservation : bookReservations){
            BookLoan bookLoan = new BookLoan();
            bookLoan.setReturned(false);
            bookLoan.setReturnDate(bookReservation.getDueDate());
            bookLoan.setLoanDate(bookLoan.getLoanDate());
            bookLoan.setUser(bookReservation.getUser());
            bookLoan.setBook(bookReservation.getBook());
        }
    }
}
