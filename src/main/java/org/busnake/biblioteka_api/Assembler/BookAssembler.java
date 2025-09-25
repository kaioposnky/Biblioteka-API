package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.BookController;
import org.busnake.biblioteka_api.Model.Entities.Book;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BookAssembler extends BaseAssembler<Book> {

    public BookAssembler(){
        super(BookController.class);
    }

}
