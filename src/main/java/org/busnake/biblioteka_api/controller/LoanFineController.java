package org.busnake.biblioteka_api.controller;

import org.busnake.biblioteka_api.assembler.LoanFineAssembler;
import org.busnake.biblioteka_api.exception.BookLoanNotFoundException;
import org.busnake.biblioteka_api.exception.LoanFineNotFoundException;
import org.busnake.biblioteka_api.model.dto.responses.LoanFineResponseDTO;
import org.busnake.biblioteka_api.model.entities.LoanFine;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.LoanFineRepository;
import org.busnake.biblioteka_api.service.LoanFineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.busnake.biblioteka_api.helper.ResponseHelper.createErrorResponse;
import static org.busnake.biblioteka_api.helper.ResponseHelper.createSuccessResponse;

@RestController
public class LoanFineController implements GenericController<LoanFine> {

    private final LoanFineService loanFineService;
    private final LoanFineRepository repository;
    private final LoanFineAssembler assembler;

    public LoanFineController(LoanFineService loanFineService, LoanFineRepository loanFineRepository, LoanFineAssembler assembler) {
        this.loanFineService = loanFineService;
        this.repository = loanFineRepository;
        this.assembler = assembler;
    }

    @GetMapping("/loans/fines")
    @Override
    public ResponseEntity<?> all() {
        List<LoanFine> loanFineList = repository.findAll();

        List<LoanFineResponseDTO> dtoList = loanFineList.stream()
                .map(LoanFineResponseDTO::new)
                .toList();

        return createSuccessResponse(
                "Multas obtidas com sucesso!",
                HttpStatus.OK,
                dtoList
        );
    }

    @GetMapping("/loans/fines/{id}")
    @Override
    public ResponseEntity<?> one(@PathVariable Long id) {
        LoanFine loanFine = repository.findById(id).orElseThrow(
                () -> new LoanFineNotFoundException(id)
        );

        return createSuccessResponse(
                "Multa obtida com sucesso!",
                HttpStatus.OK,
                assembler.toModel(loanFine)
        );
    }

    @DeleteMapping("/loans/fines/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {

        LoanFine loanFine = repository.findById(id).orElseThrow(
                () -> new LoanFineNotFoundException(id)
        );

        repository.deleteById(id);
        return createSuccessResponse(
                "Multa deletada com sucesso!",
                HttpStatus.OK,
                assembler.toModel(loanFine)
        );
    }

    @PostMapping("/loans/fines/{id}/pay")
    public ResponseEntity<?> pay(@PathVariable Long id, @AuthenticationPrincipal User user){
        try {
            LoanFine loanFine = loanFineService.payLoanFine(id, user.getId());

            return createSuccessResponse(
                    "Multa paga com sucesso!",
                    HttpStatus.OK,
                    assembler.toModel(loanFine)
            );

        } catch (BookLoanNotFoundException ex) {
            return createErrorResponse("Empréstimo não encontrado!", HttpStatus.NOT_FOUND);
        } catch (LoanFineNotFoundException ex){
            return createErrorResponse("Multa não encontrada!", HttpStatus.NOT_FOUND);
        }
        catch (IllegalStateException ex) {
            return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
