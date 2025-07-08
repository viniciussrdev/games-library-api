package dev.viniciussr.gameslibrary.exception.game;

import dev.viniciussr.gameslibrary.exception.BusinessException;

// Game não encontrado
public class GameNotFoundException extends BusinessException {
    public GameNotFoundException(String message) {
        super(message);
    }
}