package dev.viniciussr.gameslibrary.dto;

import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.enums.Platforms;
import dev.viniciussr.gameslibrary.model.Game;
import jakarta.validation.constraints.*;

import java.util.Set;

public record GameDTO(

        Long idGame,

        @NotBlank(message = "TÍTULO é campo obrigatório")
        String title,

        @NotNull(message = "GÊNERO é campo obrigatório")
        @Pattern(regexp = "ACTION|ADVENTURE|BATTLE_ROYALE|FIGHTING|FPS|HACK_N_SLASH|HORROR|METROIDVANIA|MOBA|" +
                "MMORPG|PLATFORMER|PUZZLE|RACING|ROGUELIKE|RPG|RTS|SIMULATION|SOULSLIKE|SPORTS|SURVIVAL",

                message = "GÊNERO deve ser um entre " +
                        "ACTION - ADVENTURE - BATTLE ROYALE - FIGHTING - FPS - HACK & SLASH - HORROR - METROIDVANIA - MOBA - " +
                        "MMORPG - PLATFORMER - PUZZLE - RACING - ROGUELIKE - RPG - RTS - SIMULATION - SOULSLIKE - SPORTS - SURVIVAL"
        )
        Genres genre,

        @NotBlank(message = "ESTÚDIO é campo obrigatório")
        String studio,

        @NotNull(message = "PLATAFORMA é campo obrigatório")
        @Pattern(regexp = "MOBILE|PC|PLAYSTATION|XBOX|NINTENDO|OTHER",

                message = "PLATAFORMA deve ser uma entre " +
                        "MOBILE - PC - PLAYSTATION - XBOX - NINTENDO - OTHER"
        )
        Set<Platforms> platform,

        @NotNull(message = "QUANTIDADE é campo obrigatório")
        @Size(min = 1, max = 100, message = "QUANTIDADE deve estar entre zero e 100")
        Integer quantity,

        @NotNull(message = "DISPONIBILIDADE é campo obrigatório")
        boolean available
) {
    public GameDTO(Game game) {
        this(
                game.getIdGame(),
                game.getTitle(),
                game.getGenre(),
                game.getStudio(),
                game.getPlatform(),
                game.getQuantity(),
                game.isAvailable()
        );
    }
}
