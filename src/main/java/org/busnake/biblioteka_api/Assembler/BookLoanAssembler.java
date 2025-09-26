package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.BookLoanController;
import org.busnake.biblioteka_api.Model.Entities.BookLoan;

public class BookLoanAssembler extends BaseAssembler<BookLoan> {
    public BookLoanAssembler() {
        super(BookLoanController.class);
    }
}
