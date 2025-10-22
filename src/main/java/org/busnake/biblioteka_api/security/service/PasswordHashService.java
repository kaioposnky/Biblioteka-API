package org.busnake.biblioteka_api.security.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHashService {
    private final Argon2PasswordEncoder argon = new Argon2PasswordEncoder(10, 32, 64, 1024, 2);

    /**
     * Transforma a senha String em uma senha hash
     * @param password Senha
     * @return Senha em hash
     */
    public String hashPassword(String password){
        return argon.encode(password);
    }

    /**
     * Checa se a senha é válida para o hash
     * @param password Senha
     * @param hash Hash da senha
     * @return Booleano se a senha bate ou não
     */
    public boolean isPasswordCorrect(String password, String hash){
        return argon.matches(password, hash);
    }
}
