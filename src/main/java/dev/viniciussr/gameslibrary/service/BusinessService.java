package dev.viniciussr.gameslibrary.service;

import dev.viniciussr.gameslibrary.enums.LoanStatus;
import dev.viniciussr.gameslibrary.model.Game;
import dev.viniciussr.gameslibrary.model.Loan;
import dev.viniciussr.gameslibrary.model.User;
import dev.viniciussr.gameslibrary.repository.GameRepository;
import dev.viniciussr.gameslibrary.repository.LoanRepository;
import dev.viniciussr.gameslibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class BusinessService {

    private GameRepository gameRepository;
    private LoanRepository loanRepository;
    private UserRepository userRepository;

    // Validar DISPONIBILIDADE do GAME
    void validateGameAvailability(Game game) {

        if (game.getQuantity() <= 0) {
            throw new RuntimeException("Game indisponível para empréstimo: " + game.getTitle() +
                    " (Quantidade: " + game.getQuantity() + ") ");
        }
    }

    // Validar limite de EMPRÉSTIMOS do USUÁRIO
    void validateUserLoanLimit(User user) {

        int maxLoans = switch (user.getPlan()) {
            case NOOB -> 1;
            case PRO -> 3;
            case LEGEND -> 5;
        };

        if (user.getActiveLoans() >= maxLoans) {
            throw new RuntimeException("Limite de empréstimos atingido para o plano: " + user.getPlan());
        }
    }

    // Atualizar QUANTIDADE do GAME após EMPRÉSTIMO ou DEVOLUÇÃO
    void updateGameQuantity(Game game, int x) {

        game.setQuantity(game.getQuantity() + x);
        game.setAvailable(game.getQuantity() > 0);
        gameRepository.save(game);
    }

    // Atualizar contagem de EMPRÉSTIMOS ATIVOS do USUÁRIO
    void updateUserLoanCount(User user, int x) {

        user.setActiveLoans(user.getActiveLoans() + x);
        userRepository.save(user);
    }

    // Atualizar status de EMPRÉSTIMO para "ATRASADO"
    public void updateLateLoans() {

        List<Loan> activeLoans = loanRepository.findByStatus(LoanStatus.ACTIVE);

        for (Loan loan : activeLoans) {
            if (loan.getLoanDate().plusDays(15).isBefore(LocalDate.now())) {
                loan.setStatus(LoanStatus.LATE);
                loanRepository.save(loan);
            }
        }
    }


}
