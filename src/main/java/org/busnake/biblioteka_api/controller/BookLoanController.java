package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.BookLoanAssembler;
import org.busnake.biblioteka_api.exception.BookLoanNotFoundException;
import org.busnake.biblioteka_api.model.dto.requests.BookLoanDTO;
import org.busnake.biblioteka_api.model.dto.responses.BookLoanResponseDTO;
import org.busnake.biblioteka_api.model.dto.responses.LoanDebtResponse;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.repository.UserRepository;
import org.busnake.biblioteka_api.service.BookLoanService;
import org.busnake.biblioteka_api.service.LoanFineService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class BookLoanController implements GenericController<BookLoan> {

    private final BookLoanRepository repository;
    private final BookLoanAssembler assembler;
    private final LoanFineService loanFineService;
    private final BookLoanService bookLoanService;
    private final Logger log = Logger.getLogger(BookLoanController.class.getName());
    private final BookRepository bookRepository;

    public BookLoanController(BookLoanRepository repository, BookLoanAssembler assembler, LoanFineService loanFineService, BookRepository bookRepository, UserRepository userRepository, BookLoanService bookLoanService) {
        this.repository = repository;
        this.assembler = assembler;
        this.loanFineService = loanFineService;
        this.bookRepository = bookRepository;
        this.bookLoanService = bookLoanService;
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

        BookLoan bookLoan = bookLoanService.saveBookLoan(currentUser.getId(), data.bookId(), data.dueDate());

        return createSuccessResponse(
                "Empréstimo de livro salvo com sucesso!",
                HttpStatus.OK,
                assembler.toModel(bookLoan)
        );
    }

    @DeleteMapping("/books/loans/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {

        BookLoan bookLoan = repository.findById(id).orElseThrow(
                () -> new BookLoanNotFoundException(id)
        );

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
                    HttpStatus.OK,
                    (Object) null
            );
        }

        log.info(bookLoan.getLoanFine().toString());

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
}
