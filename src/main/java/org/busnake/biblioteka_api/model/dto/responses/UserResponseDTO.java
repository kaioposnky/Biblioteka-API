package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.user.User;

import java.time.LocalDate;

public class UserResponseDTO implements ResponseDTO<User, UserResponseDTO> {
    private Long id;
    private String name;
    private String email;
    private LocalDate dateCreated;

    public UserResponseDTO() {}

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.dateCreated = user.getDateCreated();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public UserResponseDTO fromEntity(User entity) {
        return new UserResponseDTO(entity);
    }
}
