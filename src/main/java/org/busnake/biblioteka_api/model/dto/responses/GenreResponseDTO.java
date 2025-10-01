package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.Genre;

import java.time.LocalDate;

public class GenreResponseDTO implements ResponseDTO<Genre, GenreResponseDTO> {
    private Long id;
    private String name;
    private LocalDate dateCreated;

    public GenreResponseDTO() {}

    public GenreResponseDTO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.dateCreated = genre.getDateCreated();
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
    public GenreResponseDTO fromEntity(Genre entity) {
        return new GenreResponseDTO(entity);
    }
}
