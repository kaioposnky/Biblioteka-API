package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.BookLoanController;
import org.busnake.biblioteka_api.Model.Entities.BookLoan;
import org.springframework.stereotype.Component;

@Component
public class BookLoanAssembler extends BaseAssembler<BookLoan> {
    public BookLoanAssembler() {
        super(BookLoanController.class);
    }
}
