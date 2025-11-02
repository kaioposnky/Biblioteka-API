package org.busnake.biblioteka_api.assembler;


import org.busnake.biblioteka_api.controller.LoanFineController;
import org.busnake.biblioteka_api.model.dto.responses.LoanFineResponseDTO;
import org.busnake.biblioteka_api.model.entities.LoanFine;
import org.springframework.stereotype.Component;

@Component
public class LoanFineAssembler extends BaseAssembler<LoanFine, LoanFineResponseDTO> {
    public LoanFineAssembler() {
        super(LoanFineController.class, (entity) -> new LoanFineResponseDTO().fromEntity(entity));
    }
}
