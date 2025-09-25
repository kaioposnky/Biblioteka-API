package org.busnake.biblioteka_api.Controller;

import org.busnake.biblioteka_api.Assembler.BookModelAssembler;
import org.busnake.biblioteka_api.Assembler.GenericAssembler;
import org.busnake.biblioteka_api.Exception.BookNotFoundException;
import org.busnake.biblioteka_api.Model.Entities.Book;
import org.busnake.biblioteka_api.Repository.BookRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.Helper.ResponseHelper.createSuccessResponse;

@RestController
public class BookController implements GenericController<Book> {
    private final BookRepository repository;
    private final BookModelAssembler assembler;

    public BookController(BookRepository repository, BookModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
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

    @PostMapping("/books/")
    @Override
    public ResponseEntity<?> save(@RequestBody Book newBook) {
        Book book = repository.save(newBook);

        return createSuccessResponse("Livro criado com sucesso!", HttpStatus.CREATED, assembler.toModel(book));
    }

    @DeleteMapping("/books/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Book book = repository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}")
    @Override
    public ResponseEntity<?> update(@RequestBody Book newBook, @PathVariable Long id) {
        Book updatedBook = repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthorId(newBook.getAuthorId());
                    book.setGenreId(newBook.getGenreId());
                    return repository.save(book);
                }).orElseGet(() -> {
                    return repository.save(newBook);
                });
        EntityModel<Book> model = assembler.toModel(updatedBook);
        return createSuccessResponse("Livro atualizado com sucesso!", HttpStatus.OK, model);
    }

}
