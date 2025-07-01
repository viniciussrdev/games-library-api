package dev.viniciussr.gameslibrary.controller;

import dev.viniciussr.gameslibrary.dto.GameDTO;
import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

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
        return gameService.findGameById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> listGames() {
        return ResponseEntity.ok(gameService.listGames());
    }

    @GetMapping("/title")
    public ResponseEntity<List<GameDTO>> listGamesByTitle(@RequestParam String title) {

        List<GameDTO> games = gameService.listGamesByTitle(title);

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<GameDTO>> listGamesByGenre(@RequestParam Genres genre) {

        List<GameDTO> games = gameService.listGamesByGenre(genre);

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("/studio")
    public ResponseEntity<List<GameDTO>> listGamesByStudio(@RequestParam String studio) {

        List<GameDTO> games = gameService.listGamesByStudio(studio);

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("/available")
    public ResponseEntity<List<GameDTO>> listAvailableGames() {
        List<GameDTO> games = gameService.listAvailableGames();

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }
}
