package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.BookLoanController;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.springframework.stereotype.Component;

@Component
public class BookLoanAssembler extends BaseAssembler<BookLoan> {
    public BookLoanAssembler() {
        super(BookLoanController.class);
    }
}
