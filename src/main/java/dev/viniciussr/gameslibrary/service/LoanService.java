package dev.viniciussr.gameslibrary.service;

import dev.viniciussr.gameslibrary.dto.GameDTO;
import dev.viniciussr.gameslibrary.dto.LoanDTO;
import dev.viniciussr.gameslibrary.enums.LoanStatus;
import dev.viniciussr.gameslibrary.model.Game;
import dev.viniciussr.gameslibrary.model.Loan;
import dev.viniciussr.gameslibrary.model.User;
import dev.viniciussr.gameslibrary.repository.GameRepository;
import dev.viniciussr.gameslibrary.repository.LoanRepository;
import dev.viniciussr.gameslibrary.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final GameService gameService;

    public LoanService(LoanRepository loanRepository, GameRepository gameRepository, UserRepository userRepository, UserService userService, GameService gameService) {
        this.loanRepository = loanRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.gameService = gameService;
    }

    // -------------------- CRUD BÁSICO --------------------

    // Criar EMPRÉSTIMO
    public LoanDTO createLoan(LoanDTO dto) {

        Game game = gameRepository.findById(dto.gameId())
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + dto.gameId()));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no id: " + dto.userId()));

        gameService.validateGameAvailability(game);
        userService.validateUserLoanLimit(user);

        Loan savedLoan = new Loan(
                game,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(15),
                LoanStatus.ACTIVE
        );

        gameService.updateGameQuantity(game, -1);
        userService.updateUserLoanCount(user, 1);

        return new LoanDTO(loanRepository.save(savedLoan));
    }

    // Atualizar EMPRÉSTIMO
    public LoanDTO updateLoan(Long id, LoanDTO dto) {

        Loan updatedLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado no id: " + id));

        Game game = gameRepository.findById(dto.gameId())
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + dto.gameId()));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no id: " + dto.userId()));

        if (updatedLoan.getStatus() == LoanStatus.RETURNED) {
            throw new RuntimeException("Não é possível alterar um empréstimo já devolvido.");
        }

        updatedLoan.setGame(game);
        updatedLoan.setUser(user);
        updatedLoan.setLoanDate(dto.loanDate());
        updatedLoan.setReturnDate(dto.returnDate());
        updatedLoan.setStatus(dto.status());

        return new LoanDTO(loanRepository.save(updatedLoan));
    }

    // Deletar EMPRÉSTIMO
    public void deleteLoan(Long id) {

        Loan deletedLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado no id: " + id));

        loanRepository.delete(deletedLoan);
    }

    // --------------- MÉTODOS COMPLEMENTARES ---------------

    // Devolver EMPRÉSTIMO
    public void returnLoan(Long id) {

        Loan returnedLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado no id: " + id));

        if (returnedLoan.getStatus() != LoanStatus.ACTIVE) {
            throw new RuntimeException("Este empréstimo já foi encerrado: " + returnedLoan.getIdLoan());
        }

        returnedLoan.setStatus(LoanStatus.RETURNED);
        returnedLoan.setReturnDate(LocalDate.now());

        gameService.updateGameQuantity(returnedLoan.getGame(), 1);
        userService.updateUserLoanCount(returnedLoan.getUser(), -1);

        loanRepository.save(returnedLoan);
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

    // Verificar EMPRÉSTIMOS ATRASADOS
    @Scheduled(cron = "0 0 0 * * *") // Todos os dias às 00h
    public void checkForLateLoans() {
        updateLateLoans();
    }

    // --------------- FILTROS ---------------

    // Buscar EMPRÉSTIMO por ID
    public LoanDTO findLoanById(Long id) {

        return loanRepository.findById(id)
                .map(LoanDTO::new)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado no id: " + id));
    }

    // Listar EMPRÉSTIMOS (Todos)
    public List<LoanDTO> listLoans() {

        List<LoanDTO> loans = loanRepository.findAll()
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado");
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por ID do GAME
    public List<LoanDTO> listLoansByGameId(Long idGame) {

        List<LoanDTO> loans = loanRepository.findByGame_IdGame(idGame)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Game no id: " + idGame);
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por ID do USER
    public List<LoanDTO> listLoansByUserId(Long idUser) {

        List<LoanDTO> loans = loanRepository.findByUser_IdUser(idUser)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Usuário no id: " + idUser);
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por DATA de EMPRÉSTIMO
    public List<LoanDTO> listLoansByLoanDate(LocalDate loanDate) {

        List<LoanDTO> loans = loanRepository.findByLoanDate(loanDate)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para a seguinte Data: " + loanDate);
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por DATA de DEVOLUÇÃO
    public List<LoanDTO> listLoansByReturnDate(LocalDate returnDate) {

        List<LoanDTO> loans = loanRepository.findByReturnDate(returnDate)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para a seguinte Data: " + returnDate);
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por STATUS
    public List<LoanDTO> listLoansByStatus(LoanStatus loanStatus) {

        List<LoanDTO> loans = loanRepository.findByStatus(loanStatus)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Status: " + loanStatus.name());
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por USERNAME (Nome de Usuário)
    public List<LoanDTO> listLoansByUserName(String userName) {

        List<LoanDTO> loans =  loanRepository.findByUser_Name(userName)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Usuário: " + userName);
        }
        return loans;
    }

    // Listar EMPRÉSTIMOS por GAME TITLE (Título do Game)
    public List<LoanDTO> listLoansByGameTitle(String gameTitle) {

        List<LoanDTO> loans =   loanRepository.findByGame_Title(gameTitle)
                .stream()
                .map(LoanDTO::new)
                .toList();

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Game: " + gameTitle);
        }
        return loans;
    }
}