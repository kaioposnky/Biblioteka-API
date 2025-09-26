package org.busnake.biblioteka_api.Model.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_fine")
public class LoanFine implements Identifiable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "book_loan_id", nullable = false)
    private BookLoan bookLoan;

    @Column(name = "cost_per_day", precision = 10, scale = 2)
    private BigDecimal costPerDay;

    @Column(name = "is_payed")
    private boolean isPayed;

    public Long getId() {
        return id;
    }

    @Override
    public String getCollectionRel() {
        return "loanfines";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookLoan getBookLoan() {
        return bookLoan;
    }

    public void setBookLoan(BookLoan bookLoan) {
        this.bookLoan = bookLoan;
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

}