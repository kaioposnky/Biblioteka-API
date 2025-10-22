package org.busnake.biblioteka_api.service;

import org.busnake.biblioteka_api.exception.BookNotFoundException;
import org.busnake.biblioteka_api.exception.UserNotFoundException;
import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
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

        if(!book.getIsAvailable()){
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
}
