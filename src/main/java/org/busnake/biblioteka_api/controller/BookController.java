package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.BookAssembler;
import org.busnake.biblioteka_api.exception.BookNotFoundException;
import org.busnake.biblioteka_api.model.dto.requests.BookDTO;
import org.busnake.biblioteka_api.model.dto.responses.BookResponseDTO;
import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.service.BookService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class BookController implements GenericController<Book> {
    private final BookRepository repository;
    private final BookAssembler assembler;
    private final BookService bookService;

    public BookController(BookRepository repository, BookAssembler assembler, BookService bookService) {
        this.repository = repository;
        this.assembler = assembler;
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<?> all() {
        List<Book> books = repository.findAll();

        return createSuccessResponse("Livros obtidos com sucesso!", HttpStatus.OK, assembler.toListModel(books));
    }

    @GetMapping("/books/{id}")
    @Override
    public ResponseEntity<?> one(@PathVariable Long id){
        Book book = repository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );

        return createSuccessResponse("Livro obtido com sucesso!", HttpStatus.OK, assembler.toModel(book));
    }

    @PostMapping("/books")
    public ResponseEntity<?> save(@RequestBody BookDTO data) {
        Book book = bookService.saveBook(data.title(), data.authorName(), data.genreName());

        return createSuccessResponse("Livro criado com sucesso!",
                HttpStatus.CREATED,
                assembler.toModel(book)
        );
    }

    @DeleteMapping("/books/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Book book = repository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );

        repository.deleteById(id);
        return createSuccessResponse("Livro deletado com sucesso!",
                HttpStatus.CREATED,
                assembler.toModel(book)
        );
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> update(@RequestBody Book newBook, @PathVariable Long id) {
        Book updatedBook = repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    return repository.save(book);
                }).orElseGet(() -> {
                    return repository.save(newBook);
                });

        return createSuccessResponse("Livro atualizado com sucesso!", HttpStatus.OK, assembler.toModel(updatedBook));
    }

    @GetMapping("/books/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String title, @RequestParam(required = false) Boolean isAvailable){
        if (title == null) title = "";
        if (isAvailable == null) isAvailable = true;
        List<Book> books = repository.findBooksByTitleAndAvailability(title, isAvailable);

        return createSuccessResponse(
                "Livros encontrados com sucesso!",
                HttpStatus.OK,
                assembler.toListModel(books)
        );
    }

}
