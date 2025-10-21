package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.AuthorController;
import org.busnake.biblioteka_api.model.dto.responses.AuthorResponseDTO;
import org.busnake.biblioteka_api.model.entities.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorAssembler extends BaseAssembler<Author, AuthorResponseDTO> {
    public AuthorAssembler(){
        super(AuthorController.class, (entity) -> new AuthorResponseDTO().fromEntity(entity));
    }
}
