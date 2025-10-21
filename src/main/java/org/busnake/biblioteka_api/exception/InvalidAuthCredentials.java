package org.busnake.biblioteka_api.exception;

public class InvalidAuthCredentials extends RuntimeException {
    public InvalidAuthCredentials() {
        super("Credenciais inválidas.");
    }
}
