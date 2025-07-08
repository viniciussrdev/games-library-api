package dev.viniciussr.gameslibrary.service;

import dev.viniciussr.gameslibrary.dto.GameDTO;
import dev.viniciussr.gameslibrary.dto.LoanDTO;
import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.model.Game;
import dev.viniciussr.gameslibrary.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // -------------------- CRUD BÁSICO --------------------

    // Criar GAME
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

    // --------------- MÉTODOS COMPLEMENTARES ---------------

    // Validar DISPONIBILIDADE do GAME
    void validateGameAvailability(Game game) {

        if (game.getQuantity() <= 0) {
            throw new RuntimeException("Game indisponível para empréstimo: " + game.getTitle() +
                    " (Quantidade: " + game.getQuantity() + ") ");
        }
    }

    // Atualizar QUANTIDADE do GAME após EMPRÉSTIMO ou DEVOLUÇÃO
    void updateGameQuantity(Game game, int x) {

        game.setQuantity(game.getQuantity() + x);
        game.setAvailable(game.getQuantity() > 0);
        gameRepository.save(game);
    }

    // --------------- FILTROS ---------------

    // Buscar GAME por ID
    public GameDTO findGameById(Long id) {

        return gameRepository.findById(id)
                .map(GameDTO::new)
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + id));
    }

    // Listar GAMES (Todos)
    public List<GameDTO> listGames() {

        List<GameDTO> games = gameRepository.findAll()
                .stream()
                .map(GameDTO::new)
                .toList();

        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum Game encontrado");
        }
        return games;
    }

    // Listar GAMES por TÍTULO
    public List<GameDTO> listGamesByTitle(String title) {

        List<GameDTO> games = gameRepository.findByTitle(title)
                .stream()
                .map(GameDTO::new)
                .toList();

        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum Game encontrado com o título: " + title);
        }
        return games;
    }

    // Listar GAMES por GÊNERO
    public List<GameDTO> listGamesByGenre(Genres genre) {

        List<GameDTO> games = gameRepository.findByGenre(genre)
                .stream()
                .map(GameDTO::new)
                .toList();

        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum Game encontrado do gênero: " + genre);
        }
        return games;
    }

    // Listar GAMES por ESTÚDIO
    public List<GameDTO> listGamesByStudio(String studio) {

        List<GameDTO> games = gameRepository.findByStudio(studio)
                .stream()
                .map(GameDTO::new)
                .toList();

        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum Game encontrado do estúdio: " + studio);
        }
        return games;
    }

    // Listar GAMES DISPONÍVEIS
    public List<GameDTO> listAvailableGames() {

        List<GameDTO> games = gameRepository.findByAvailableTrue()
                .stream()
                .map(GameDTO::new)
                .toList();

        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum Game disponível no momento.");
        }
        return games;
    }
}