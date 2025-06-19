package dev.viniciussr.gameslibrary.repository;

import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTitle(String title);

    List<Game> findByGenre(Genres genre);

    List<Game> findByStudio(String studio);

    List<Game> findByAvailableTrue();
}