package org.busnake.biblioteka_api.model.dto.responses;

import org.busnake.biblioteka_api.model.entities.user.UserRole;

public class LoginResponseDTO {
    private String jwtToken;
    private Long userId;
    private String name;
    private String email;
    private UserRole role;

    public LoginResponseDTO(String jwtToken, Long userId, String name, String email, UserRole role) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
