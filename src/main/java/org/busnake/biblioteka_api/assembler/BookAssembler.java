package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.BookController;
import org.busnake.biblioteka_api.model.dto.responses.BookResponseDTO;
import org.busnake.biblioteka_api.model.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookAssembler extends BaseAssembler<Book, BookResponseDTO> {

    public BookAssembler(){
        super(BookController.class, (entity) -> new BookResponseDTO().fromEntity(entity));
    }

}
