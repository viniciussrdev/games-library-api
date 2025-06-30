package dev.viniciussr.gameslibrary.model;

import dev.viniciussr.gameslibrary.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_loan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loan")
    private Long idLoan; // ID

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game; // Jogo

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Usuário

    private LocalDate loanDate; // Data Empréstimo

    private LocalDate returnDate; // Data Devolução

    @Enumerated(EnumType.STRING)
    private LoanStatus status; // Status Empréstimo

    public Loan(
            Game game,
            User user,
            LocalDate loanDate,
            LocalDate returnDate,
            LoanStatus status
    ) {
        this.game = game;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }
}