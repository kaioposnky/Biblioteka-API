package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.BookLoanAssembler;
import org.busnake.biblioteka_api.exception.BookLoanNotFoundException;
import org.busnake.biblioteka_api.exception.BookNotFoundException;
import org.busnake.biblioteka_api.exception.LoanFineNotFoundException;
import org.busnake.biblioteka_api.exception.UserNotFoundException;
import org.busnake.biblioteka_api.model.dto.requests.BookLoanDTO;
import org.busnake.biblioteka_api.model.dto.requests.BookReservationRequestDTO;
import org.busnake.biblioteka_api.model.dto.responses.BookLoanResponseDTO;
import org.busnake.biblioteka_api.model.dto.responses.LoanDebtResponse;
import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.BookReservation;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.busnake.biblioteka_api.service.BookLoanService;
import org.busnake.biblioteka_api.service.BookReservationService;
import org.busnake.biblioteka_api.service.LoanFineService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;
import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class BookLoanController implements GenericController<BookLoan> {

    private final BookLoanRepository repository;
    private final BookLoanAssembler assembler;
    private final LoanFineService loanFineService;
    private final BookLoanService bookLoanService;
    private final BookReservationService bookReservationService;

    public BookLoanController(BookLoanRepository repository, BookLoanAssembler assembler, LoanFineService loanFineService, BookLoanService bookLoanService, BookReservationService bookReservationService) {
        this.repository = repository;
        this.assembler = assembler;
        this.loanFineService = loanFineService;
        this.bookLoanService = bookLoanService;
        this.bookReservationService = bookReservationService;
    }

    @GetMapping("/books/loans")
    @Override
    public ResponseEntity<?> all() {
        List<BookLoan> bookLoanList = repository.findAll();

        List<BookLoanResponseDTO> dtoList = bookLoanList.stream()
                .map(BookLoanResponseDTO::new)
                .toList();

        return createSuccessResponse(
                "Empréstimos de livros obtidos com sucesso!",
                HttpStatus.OK,
                dtoList
        );
    }

    @GetMapping("/books/loans/{id}")
    @Override
    public ResponseEntity<?> one(@PathVariable  Long id) {

        BookLoan bookLoan = repository.findById(id).orElseThrow(
                () -> new BookLoanNotFoundException(id)
        );

        return createSuccessResponse(
                "Empréstimo de livro obtido com sucesso!",
                HttpStatus.OK,
                assembler.toModel(bookLoan)
        );
    }

    @PostMapping("/books/loans")
    public ResponseEntity<?> save(@RequestBody BookLoanDTO data, @AuthenticationPrincipal User currentUser) {

        BookLoan bookLoan;
        try{
            bookLoan = bookLoanService.saveBookLoan(currentUser.getId(), data.bookId(), data.dueDate());
        } catch (BookNotFoundException | UserNotFoundException ex){
            return createErrorResponse("Livro ou usuário não encontrados!", HttpStatus.NOT_FOUND);
        }

        if(bookLoan == null){
            return createErrorResponse("Livro indisponível para empréstimo.", HttpStatus.BAD_REQUEST);
        }

        return createSuccessResponse(
                "Empréstimo de livro salvo com sucesso!",
                HttpStatus.CREATED,
                assembler.toModel(bookLoan)
        );
    }

    @DeleteMapping("/books/loans/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {

        BookLoan bookLoan = repository.findById(id).orElseThrow(
                () -> new BookLoanNotFoundException(id)
        );

        repository.deleteById(id);

        return createSuccessResponse(
                "Empréstimo de livro deletado com sucesso!",
                HttpStatus.OK,
                assembler.toModel(bookLoan)
        );
    }

    @PutMapping("/books/loans/{id}")
    public ResponseEntity<?> update(@RequestBody BookLoan newBookLoan, @PathVariable Long id) {

        BookLoan updatedBookLoan = repository.findById(id).
                map((bookLoan -> {
                    bookLoan.setIsReturned(newBookLoan.getIsReturned());
                    return repository.save(bookLoan);
                })
                ).orElseGet(() -> {
                    return repository.save(newBookLoan);
                });

        return createSuccessResponse(
                "Empréstimo de livro atualizado com sucesso!",
                HttpStatus.OK,
                assembler.toModel(updatedBookLoan)
        );
    }

    @GetMapping("/books/loans/user")
    public ResponseEntity<?> fromUser(@AuthenticationPrincipal User currentUser) {

        List<BookLoan> bookLoans = repository.findBookLoansByUserId(currentUser.getId()).stream().toList();

        return createSuccessResponse(
                "Empréstimos encontrados com sucesso!",
                HttpStatus.OK,
                assembler.toListModel(bookLoans)
        );
    }

    @GetMapping("/books/loans/{id}/fine")
    public ResponseEntity<?> debt(@PathVariable Long id){

        BookLoan bookLoan = repository.findById(id).orElseThrow(
                () -> new BookLoanNotFoundException(id)
        );

        if (bookLoan.getLoanFine() == null) {
            return createSuccessResponse(
                    "Não existe multa para este empréstimo.",
                    HttpStatus.NOT_FOUND,
                    (Object) null
            );
        }

        LoanDebtResponse responseModel = new LoanDebtResponse(
                bookLoan.getBook(), bookLoan, bookLoan.getLoanFine());

        double loanFine = loanFineService.calculateLoanFine(bookLoan);
        responseModel.setTotalAmount(loanFine);

        return createSuccessResponse(
                "Informações da multa do empréstimo obtidas com sucesso!",
                HttpStatus.OK,
                EntityModel.of(responseModel)
        );
    }

    @PostMapping("/books/{bookId}/loans/reservate")
    public ResponseEntity<?> reservate(@PathVariable Long bookId, @RequestBody BookReservationRequestDTO request, @AuthenticationPrincipal User user){
        try {
            BookReservation bookReservation = bookReservationService.createReservation(
                    bookId, 
                    user.getId(), 
                    request.loanStartDate(), 
                    request.dueDate()
            );

            return createSuccessResponse(
                    "Reserva de livro criada com sucesso!",
                    HttpStatus.CREATED,
                    bookReservation
            );

        } catch (BookNotFoundException ex) {
            return createErrorResponse("Livro não encontrado!", HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException ex) {
            return createErrorResponse("Usuário não encontrado!", HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/books/loans/{id}/renovate")
    public ResponseEntity<?> renovate(@PathVariable Long id, @RequestBody LocalDate newDueDate, @AuthenticationPrincipal User user){
        try {
            BookLoan renewedBookLoan = bookLoanService.renewBookLoan(id, newDueDate, user.getId());

            return createSuccessResponse(
                    "Empréstimo renovado com sucesso!",
                    HttpStatus.OK,
                    assembler.toModel(renewedBookLoan)
            );

        } catch (BookLoanNotFoundException ex) {
            return createErrorResponse("Empréstimo não encontrado!", HttpStatus.NOT_FOUND);
        } catch (IllegalStateException ex) {
            return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
