package dev.viniciussr.gameslibrary.service;

import dev.viniciussr.gameslibrary.dto.GameDTO;
import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.model.Game;
import dev.viniciussr.gameslibrary.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // -------------------- CRUD BÁSICO --------------------

    // Salvar/Criar GAME
    public GameDTO createGame(GameDTO dto) {

        Game savedGame = new Game(
                dto.title(),
                dto.genre(),
                dto.studio(),
                dto.platform(),
                dto.quantity(),
                dto.available()
        );
        return new GameDTO(gameRepository.save(savedGame));
    }

    // Atualizar GAME
    public GameDTO updateGame(Long id, GameDTO dto) {

        Game updatedGame = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + id));

        updatedGame.setTitle(dto.title());
        updatedGame.setGenre(dto.genre());
        updatedGame.setStudio(dto.studio());
        updatedGame.setPlatform(dto.platform());
        updatedGame.setQuantity(dto.quantity());
        updatedGame.setAvailable(dto.available());

        return new GameDTO(gameRepository.save(updatedGame));
    }

    // Deletar GAME
    public void deleteGame(Long id) {

        Game deletedGame = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + id));

        gameRepository.delete(deletedGame);
    }

    // --------------- MÉTODOS DE BUSCA/LISTA ---------------

    // Buscar GAME por ID
    public GameDTO findGameById(Long id) {
        return gameRepository.findById(id)
                .map(GameDTO::new)
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + id));
    }

    // Listar GAMES (Todos)
    public List<GameDTO> listGames() {

        return gameRepository.findAll()
                .stream()
                .map(GameDTO::new)
                .toList();
    }

    // Listar GAMES por TÍTULO
    public List<GameDTO> listGamesByTitle(String title) {

        return gameRepository.findByTitle(title)
                .stream()
                .map(GameDTO::new)
                .toList();
    }

    // Listar GAMES por GÊNERO
    public List<GameDTO> listGamesByGenre(Genres genre) {

        return gameRepository.findByGenre(genre)
                .stream()
                .map(GameDTO::new)
                .toList();
    }

    // Listar GAMES por ESTÚDIO
    public List<GameDTO> listGamesByStudio(String studio) {

        return gameRepository.findByStudio(studio)
                .stream()
                .map(GameDTO::new)
                .toList();
    }

    // Listar GAMES DISPONÍVEIS
    public List<GameDTO> listAvailableGames() {

        return gameRepository.findByAvailableTrue()
                .stream()
                .map(GameDTO::new)
                .toList();
    }
}