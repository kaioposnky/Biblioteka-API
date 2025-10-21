package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.GenreAssembler;
import org.busnake.biblioteka_api.exception.GenreNotFoundException;
import org.busnake.biblioteka_api.model.entities.Genre;
import org.busnake.biblioteka_api.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class GenreController implements GenericController<Genre>{
    private final GenreRepository repository;
    private final GenreAssembler assembler;

    public GenreController(GenreRepository repository, GenreAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/genres")
    @Override
    public ResponseEntity<?> all() {

        List<Genre> genres = repository.findAll();

        return createSuccessResponse("Gêneros encontrados com sucesso!", HttpStatus.OK,
                assembler.toListModel(genres));
    }

    @GetMapping("/genres/{id}")
    @Override
    public ResponseEntity<?> one(@PathVariable Long id) {
        Genre genre = repository.findById(id).orElseThrow(
                () -> new GenreNotFoundException(id)
        );

        return createSuccessResponse("Gênero encontrado com sucesso!", HttpStatus.OK,
                assembler.toModel(genre));
    }

    @DeleteMapping("/genres/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Genre genre = repository.findById(id).orElseThrow(
                () -> new GenreNotFoundException(id)
        );

        repository.deleteById(id);
        return createSuccessResponse("Gênero deletado com sucesso!",
                HttpStatus.CREATED,
                assembler.toModel(genre)
        );
    }
}
