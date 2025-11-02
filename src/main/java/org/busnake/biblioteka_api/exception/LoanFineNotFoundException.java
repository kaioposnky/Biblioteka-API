package org.busnake.biblioteka_api.exception;

public class LoanFineNotFoundException extends EntityNotFoundException {
    public LoanFineNotFoundException(Long id) {
        super("Multa com id " + id +  " não encontrada!");
    }
}
