package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.Book;

import java.time.LocalDate;

public class BookResponseDTO implements ResponseDTO<Book, BookResponseDTO> {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private Long genreId;
    private String genreName;
    private LocalDate dateCreated;
    private Boolean isAvailable;

    public BookResponseDTO() {}

    public BookResponseDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.dateCreated = book.getDateCreated();
        this.isAvailable = book.getIsAvailable();
        
        if (book.getAuthor() != null) {
            this.authorId = book.getAuthor().getId();
            this.authorName = book.getAuthor().getName();
        }
        
        if (book.getGenre() != null) {
            this.genreId = book.getGenre().getId();
            this.genreName = book.getGenre().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public BookResponseDTO fromEntity(Book entity) {
        return new BookResponseDTO(entity);
    }
}
