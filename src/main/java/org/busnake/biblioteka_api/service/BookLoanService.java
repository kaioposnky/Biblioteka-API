package org.busnake.biblioteka_api.service;

import org.busnake.biblioteka_api.exception.BookNotFoundException;
import org.busnake.biblioteka_api.exception.UserNotFoundException;
import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.repository.UserRepository;

import java.time.LocalDate;

public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BookLoanService(BookLoanRepository bookLoanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BookLoan saveBookLoan(Long userId, Long bookId, LocalDate dueDate) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(bookId)
        );

        BookLoan bookLoan = new BookLoan();
        bookLoan.setUser(user);
        bookLoan.setBook(book);
        bookLoan.setDueDate(dueDate);
        bookLoan.setIsReturned(false);

        return bookLoan;
    }
}
