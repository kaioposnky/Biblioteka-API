package org.busnake.biblioteka_api.tasks;

import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.repository.BookLoanRepository;
import org.busnake.biblioteka_api.service.BookLoanService;
import org.busnake.biblioteka_api.service.LoanFineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class FineGenerationTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(FineGenerationTask.class);

    private final BookLoanRepository bookLoanRepository;
    private final LoanFineService loanFineService;

    public FineGenerationTask(BookLoanRepository bookLoanRepository, BookLoanService bookLoanService, LoanFineService loanFineService) {
        this.bookLoanRepository = bookLoanRepository;
        this.loanFineService = loanFineService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void generateOverdueLoansFine(){

        LOGGER.info("Procurando por livros com atrasos...");
        // Obtêm todos os livros cuja expiração é
        List<BookLoan> bookLoans = bookLoanRepository.findAllByDueDateBeforeAndReturnDateIsNullAndLoanFineIsNull(LocalDate.now());
        if(!bookLoans.isEmpty()){
            LOGGER.info("Gerando multas para livros com atrasos...");
            loanFineService.generateLoanFines(bookLoans);
            LOGGER.info("{} Multas geradas com sucesso!", bookLoans.size());
        } else {
            LOGGER.info("Nenhum livro com atraso encontrado!");
        }
    }
}
