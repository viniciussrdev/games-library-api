package dev.viniciussr.gameslibrary.exception.game;

import dev.viniciussr.gameslibrary.exception.BusinessException;
import dev.viniciussr.gameslibrary.model.Game;

// Game Indisponível para Empréstimo
public class GameUnavailableException extends BusinessException {
    public GameUnavailableException(Game game ) {
        super("Game indisponível para empréstimo: " + game.getTitle() +
                " (Quantidade: " + game.getQuantity() + ") ");;
    }
}

