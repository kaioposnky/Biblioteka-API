package org.busnake.biblioteka_api.service;

import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class LoanFineService {


    /**
     * Calcula a multa de empréstimo baseada na data de entrega esperada e a data de entrega real.
     * @param bookLoan Entidade de empréstimo do livro
     * @return Valor da multa em double
     */
    public double calculateLoanFine(BookLoan bookLoan) {
        try{

            BigDecimal costPerDay = bookLoan.getLoanFine().getCostPerDay();

            // Dias entre a data de entrega e a data de entrega
            long daysDifference;
            if(bookLoan.getReturnDate() == null){
                // Se não entregou ainda checa para o dia de hoje
                daysDifference = ChronoUnit.DAYS.between(bookLoan.getDueDate(), LocalDate.now());
            } else{
                daysDifference = ChronoUnit.DAYS.between(bookLoan.getDueDate(), bookLoan.getReturnDate());
            }
            BigDecimal totalCost = BigDecimal.ZERO;
            for(int i = 0; i < daysDifference; i++){
                totalCost = totalCost.add(costPerDay);
            }

            return totalCost.doubleValue();
        } catch (Exception ex){
            throw ex;
        }
    }
}
