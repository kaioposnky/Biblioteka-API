package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.Author;

import java.time.LocalDate;

public class AuthorResponseDTO implements ResponseDTO<Author, AuthorResponseDTO> {
    private Long id;
    private String name;
    private LocalDate dateCreated;

    public AuthorResponseDTO() {}

    public AuthorResponseDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.dateCreated = author.getDateCreated();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public AuthorResponseDTO fromEntity(Author author) {
        return new AuthorResponseDTO(author);
    }
}
