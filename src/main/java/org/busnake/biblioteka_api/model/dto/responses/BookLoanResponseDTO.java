package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.BookLoan;

import java.time.LocalDate;

public class BookLoanResponseDTO implements ResponseDTO<BookLoan, BookLoanResponseDTO> {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long bookId;
    private String bookTitle;
    private String authorName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Boolean isReturned;

    public BookLoanResponseDTO() {
    }

    public BookLoanResponseDTO(BookLoan bookLoan) {
        this.id = bookLoan.getId();
        this.userId = bookLoan.getUser().getId();
        this.userName = bookLoan.getUser().getName();
        this.userEmail = bookLoan.getUser().getEmail();
        this.bookId = bookLoan.getBook().getId();
        this.bookTitle = bookLoan.getBook().getTitle();
        this.authorName = bookLoan.getBook().getAuthor().getName();
        this.loanDate = bookLoan.getLoanDate();
        this.dueDate = bookLoan.getDueDate();
        this.returnDate = bookLoan.getReturnDate();
        this.isReturned = bookLoan.getIsReturned();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }

    @Override
    public BookLoanResponseDTO fromEntity(BookLoan entity) {
        return new BookLoanResponseDTO(entity);
    }
}
