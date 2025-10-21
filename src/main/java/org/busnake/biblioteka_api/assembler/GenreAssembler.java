package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.GenericController;
import org.busnake.biblioteka_api.model.dto.responses.GenreResponseDTO;
import org.busnake.biblioteka_api.model.entities.Genre;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GenreAssembler extends BaseAssembler<Genre, GenreResponseDTO> {
    public GenreAssembler(Class<? extends GenericController<Genre>> controllerClass, Function<Genre, GenreResponseDTO> toDtoConverter) {
        super(controllerClass, toDtoConverter);
    }
}
