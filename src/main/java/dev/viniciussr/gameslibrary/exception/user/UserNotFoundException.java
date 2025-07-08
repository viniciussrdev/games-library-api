package dev.viniciussr.gameslibrary.exception.user;

import dev.viniciussr.gameslibrary.exception.BusinessException;

// Usuário não encontrado
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
