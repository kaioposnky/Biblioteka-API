package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.GenericController;
import org.busnake.biblioteka_api.model.dto.responses.AuthorResponseDTO;
import org.busnake.biblioteka_api.model.entities.Author;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AuthorAssembler extends BaseAssembler<Author, AuthorResponseDTO> {
    public AuthorAssembler(Class<? extends GenericController<Author>> controllerClass, Function<Author, AuthorResponseDTO> toDtoConverter) {
        super(controllerClass, toDtoConverter);
    }
}
