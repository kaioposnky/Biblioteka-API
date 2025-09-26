package org.busnake.biblioteka_api.Controller;

import org.busnake.biblioteka_api.Assembler.BookLoanAssembler;
import org.busnake.biblioteka_api.Exception.BookLoanNotFoundException;
import org.busnake.biblioteka_api.Model.Entities.BookLoan;
import org.busnake.biblioteka_api.Repository.BookLoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.Helper.ResponseHelper.createSuccessResponse;

public class BookLoanController implements GenericController<BookLoan> {

    private final BookLoanRepository repository;
    private final BookLoanAssembler assembler;

    public BookLoanController(BookLoanRepository repository, BookLoanAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/books/loans")
    @Override
    public ResponseEntity<?> all() {
        List<BookLoan> bookLoanList = repository.findAll();

        return createSuccessResponse(
                "Empréstimos de livros obtidos com sucesso!",
                HttpStatus.OK,
                assembler.toListModel(bookLoanList)
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
    @Override
    public ResponseEntity<?> save(@RequestBody BookLoan entity) {

        BookLoan bookLoan = repository.save(entity);

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
    @Override
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
}
