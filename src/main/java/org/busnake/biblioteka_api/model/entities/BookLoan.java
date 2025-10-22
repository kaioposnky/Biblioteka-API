package org.busnake.biblioteka_api.model.entities;

import jakarta.persistence.*;
import org.busnake.biblioteka_api.model.entities.user.User;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "book_loan")
public class BookLoan implements Identifiable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @OneToOne(mappedBy = "bookLoan", cascade = CascadeType.ALL, orphanRemoval = true)
    private LoanFine loanFine;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ColumnDefault("false")
    @Column(name = "is_returned")
    private Boolean isReturned;

    public BookLoan(){}

    public BookLoan(BookRepository bookRepository, User user, Long bookId, LocalDate returnDate){
        this.book = bookRepository.findById(bookId).orElseThrow();
        this.returnDate = returnDate;
        this.dueDate = LocalDate.MIN;
        this.loanDate = LocalDate.now();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getCollectionRel() {
        return "bookloans";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    public Boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Boolean isReturned) {
        this.isReturned = isReturned;
    }

    public LoanFine getLoanFine() {
        return loanFine;
    }

    public void setLoanFine(LoanFine loanFine) {
        this.loanFine = loanFine;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }
}