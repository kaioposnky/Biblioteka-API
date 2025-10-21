package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.GenreController;
import org.busnake.biblioteka_api.model.dto.responses.GenreResponseDTO;
import org.busnake.biblioteka_api.model.entities.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreAssembler extends BaseAssembler<Genre, GenreResponseDTO> {
    public GenreAssembler(){
        super(GenreController.class, (entity) -> new GenreResponseDTO().fromEntity(entity));
    }
}