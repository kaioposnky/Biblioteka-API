package org.busnake.biblioteka_api.tasks;

import org.busnake.biblioteka_api.model.entities.BookReservation;
import org.busnake.biblioteka_api.repository.BookReservationRepository;
import org.busnake.biblioteka_api.service.BookLoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class ReservationGenerationTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationGenerationTask.class);
    
    private final BookReservationRepository bookReservationRepository;
    private final BookLoanService bookLoanService;

    public ReservationGenerationTask(BookReservationRepository bookReservationRepository, BookLoanService bookLoanService) {
        this.bookReservationRepository = bookReservationRepository;
        this.bookLoanService = bookLoanService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void generateReservations(){

        LOGGER.info("Procurando reservas de livro para gerar emprestimos...");

        List<BookReservation> bookReservations = bookReservationRepository.findAllByLoanStartDateGreaterThanEqualAndBookLoanIsNull(LocalDate.now());
        if(bookReservations.isEmpty()){
            LOGGER.info("Nenhuma reserva pendente encontrada.");
        } else{
            bookLoanService.generateBookLoansFromReservations(bookReservations);
            LOGGER.info("Reservas pendentes geradas com sucesso!");
        }
    }
}
