package dev.viniciussr.gameslibrary.controller;

import dev.viniciussr.gameslibrary.dto.GameDTO;
import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody GameDTO dto) {
        return ResponseEntity.ok(gameService.updateGame(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> findGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.findGameById(id));
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> listGames() {
        return ResponseEntity.ok(gameService.listGames());
    }

    @GetMapping(params = "title")
    public ResponseEntity<List<GameDTO>> listGamesByTitle(@RequestParam String title) {
        return ResponseEntity.ok(gameService.listGamesByTitle(title));
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<GameDTO>> listGamesByGenre(@RequestParam Genres genre) {
        return ResponseEntity.ok(gameService.listGamesByGenre(genre));
    }

    @GetMapping(params = "studio")
    public ResponseEntity<List<GameDTO>> listGamesByStudio(@RequestParam String studio) {
        return ResponseEntity.ok(gameService.listGamesByStudio(studio));
    }

    @GetMapping("/available")
    public ResponseEntity<List<GameDTO>> listAvailableGames() {
        return ResponseEntity.ok(gameService.listAvailableGames());
    }
}
