package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.LoanFine;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanFineResponseDTO implements ResponseDTO<LoanFine, LoanFineResponseDTO> {
    private Long id;
    private Long bookLoanId;
    private String bookTitle;
    private String userName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BigDecimal costPerDay;
    private boolean isPayed;

    public LoanFineResponseDTO() {}

    public LoanFineResponseDTO(LoanFine loanFine) {
        this.id = loanFine.getId();
        this.costPerDay = loanFine.getCostPerDay();
        this.isPayed = loanFine.isPayed();
        
        if (loanFine.getBookLoan() != null) {
            this.bookLoanId = loanFine.getBookLoan().getId();
            this.loanDate = loanFine.getBookLoan().getLoanDate();
            this.dueDate = loanFine.getBookLoan().getDueDate();
            this.returnDate = loanFine.getBookLoan().getReturnDate();
            
            if (loanFine.getBookLoan().getUser() != null) {
                this.userName = loanFine.getBookLoan().getUser().getName();
            }
            
            if (loanFine.getBookLoan().getBook() != null) {
                this.bookTitle = loanFine.getBookLoan().getBook().getTitle();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookLoanId() {
        return bookLoanId;
    }

    public void setBookLoanId(Long bookLoanId) {
        this.bookLoanId = bookLoanId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    @Override
    public LoanFineResponseDTO fromEntity(LoanFine entity) {
        return new LoanFineResponseDTO(entity);
    }
}
