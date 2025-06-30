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
    private Long idGame; // ID

    private String title; // Título

    @Enumerated(EnumType.STRING)
    private Genres genre; // Gênero

    private String studio; // Estúdio

    @ElementCollection(targetClass = Platforms.class)
    @Enumerated(EnumType.STRING)
    private Set<Platforms> platform; // Plataforma

    private Integer quantity; // Quantidade

    private boolean available; // Disponível(?)

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