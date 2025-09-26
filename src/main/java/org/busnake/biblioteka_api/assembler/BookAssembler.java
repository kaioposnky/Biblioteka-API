package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.BookController;
import org.busnake.biblioteka_api.model.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookAssembler extends BaseAssembler<Book> {

    public BookAssembler(){
        super(BookController.class);
    }

}
