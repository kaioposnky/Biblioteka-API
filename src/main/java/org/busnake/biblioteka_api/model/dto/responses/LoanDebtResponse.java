package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.model.entities.BookLoan;
import org.busnake.biblioteka_api.model.entities.LoanFine;

import java.time.LocalDate;

public class LoanDebtResponse {

    private Long loanFineId;
    private Long bookLoanId;
    private String bookTitle;
    private String authorName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private boolean isPayed;
    private double totalAmount;
    private double costPerDay;

    public LoanDebtResponse(Book book, BookLoan bookLoan, LoanFine loanFine){
        this.bookLoanId = bookLoan.getId();
        this.bookTitle = book.getTitle();
        this.authorName = book.getAuthor().getName();
        this.loanFineId = loanFine.getId();
        this.loanDate = bookLoan.getLoanDate();
        this.dueDate = bookLoan.getDueDate();
        this.returnDate = bookLoan.getReturnDate();
        this.isReturned = bookLoan.getIsReturned();
        this.isPayed = loanFine.isPayed();
        this.costPerDay = loanFine.getCostPerDay().doubleValue();
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public Long getBookLoanId() {
        return bookLoanId;
    }

    public void setBookLoanId(Long bookLoanId) {
        this.bookLoanId = bookLoanId;
    }

    public Long getLoanFineId() {
        return loanFineId;
    }

    public void setLoanFineId(Long loanFineId) {
        this.loanFineId = loanFineId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
