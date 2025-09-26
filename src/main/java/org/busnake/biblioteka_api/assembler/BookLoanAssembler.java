package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.BookLoanController;
import org.busnake.biblioteka_api.model.dto.responses.BookLoanResponseDTO;
import org.busnake.biblioteka_api.model.dto.responses.BookResponseDTO;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.springframework.stereotype.Component;

@Component
public class BookLoanAssembler extends BaseAssembler<BookLoan, BookLoanResponseDTO> {
    public BookLoanAssembler() {
        super(BookLoanController.class, (entity) -> new BookLoanResponseDTO().fromEntity(entity));
    }
}
