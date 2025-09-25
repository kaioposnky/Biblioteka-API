package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.BookController;
import org.busnake.biblioteka_api.Model.Entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookModelAssembler extends GenericAssembler<Book> {

    @Autowired
    public BookModelAssembler(BookController controller){
        super(controller);
    }

}
