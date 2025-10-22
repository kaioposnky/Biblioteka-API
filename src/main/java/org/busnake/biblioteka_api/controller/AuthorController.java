package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.AuthorAssembler;
import org.busnake.biblioteka_api.exception.AuthorNotFoundException;
import org.busnake.biblioteka_api.model.entities.Author;
import org.busnake.biblioteka_api.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class AuthorController implements GenericController<Author>{
    private final AuthorRepository repository;
    private final AuthorAssembler assembler;

    public AuthorController(AuthorRepository repository, AuthorAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/authors")
    @Override
    public ResponseEntity<?> all() {

        List<Author> authors = repository.findAll();

        return createSuccessResponse("Autores encontrados com sucesso!", HttpStatus.OK,
                assembler.toListModel(authors));
    }

    @GetMapping("/authors/{id}")
    @Override
    public ResponseEntity<?> one(@PathVariable Long id) {
        Author author = repository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException(id)
        );

        return createSuccessResponse("Autor encontrado com sucesso!", HttpStatus.OK,
                assembler.toModel(author));
    }

    @DeleteMapping("/authors/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Author author = repository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException(id)
        );

        repository.deleteById(id);
        return createSuccessResponse("Autor deletado com sucesso!",
                HttpStatus.CREATED,
                assembler.toModel(author)
        );
    }
}
