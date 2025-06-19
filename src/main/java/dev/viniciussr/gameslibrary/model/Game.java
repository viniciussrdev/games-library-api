package dev.viniciussr.gameslibrary.model;

import dev.viniciussr.gameslibrary.enums.Genres;
import dev.viniciussr.gameslibrary.enums.Platforms;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tb_game")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private Long idGame;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    private String studio;

    @ElementCollection(targetClass = Platforms.class)
    @Enumerated(EnumType.STRING)
    private Set<Platforms> platform;

    private Integer quantity;

    private boolean available;

    public Game(
            String title,
            Genres genre,
            String studio,
            Set<Platforms> platform,
            Integer quantity,
            boolean available
    ) {
        this.title = title;
        this.genre = genre;
        this.studio = studio;
        this.platform = platform;
        this.quantity = quantity;
        this.available = available;
    }
}