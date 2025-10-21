package org.busnake.biblioteka_api.model.dto.responses;

public class LoginResponseDTO {
    private String jwtToken;
    private Long userId;
    private String name;
    private String email;

    public LoginResponseDTO(String jwtToken, Long userId, String name, String email) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.name = name;
        this.email = email;
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

}
