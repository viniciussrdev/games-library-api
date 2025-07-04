package dev.viniciussr.gameslibrary.service;

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

    private final BusinessService businessService;

    public LoanService(LoanRepository loanRepository, GameRepository gameRepository, UserRepository userRepository, BusinessService businessService) {
        this.loanRepository = loanRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.businessService = businessService;
    }

    // -------------------- CRUD BÁSICO --------------------

    // Salvar/Criar EMPRÉSTIMO
    public LoanDTO createLoan(LoanDTO dto) {

        Game game = gameRepository.findById(dto.gameId())
                .orElseThrow(() -> new RuntimeException("Game não encontrado no id: " + dto.gameId()));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no id: " + dto.userId()));

        businessService.validateGameAvailability(game);
        businessService.validateUserLoanLimit(user);

        Loan savedLoan = new Loan(
                game,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(15),
                LoanStatus.ACTIVE
        );

        businessService.updateGameQuantity(game, -1);
        businessService.updateUserLoanCount(user, 1);

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

    // --------------- MÉTODOS DE SERVIÇO ---------------

    // Devolver EMPRÉSTIMO
    public void returnLoan(Long id) {

        Loan returnedLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado no id: " + id));

        if (returnedLoan.getStatus() != LoanStatus.ACTIVE) {
            throw new RuntimeException("Este empréstimo já foi encerrado: " + returnedLoan.getIdLoan());
        }

        returnedLoan.setStatus(LoanStatus.RETURNED);
        returnedLoan.setReturnDate(LocalDate.now());

        businessService.updateGameQuantity(returnedLoan.getGame(), 1);
        businessService.updateUserLoanCount(returnedLoan.getUser(), -1);

        loanRepository.save(returnedLoan);
    }

    // Verificar EMPRÉSTIMOS ATRASADOS
    @Scheduled(cron = "0 0 0 * * *") // Todos os dias às 00h
    public void checkForLateLoans() {
        businessService.updateLateLoans();
    }

    // --------------- MÉTODOS DE BUSCA/LISTA ---------------

    // Buscar EMPRÉSTIMO por ID
    public Optional<LoanDTO> findLoanById(Long id) {

        return loanRepository.findById(id)
                .map(LoanDTO::new);
    }

    // Listar EMPRÉSTIMOS (Todos)
    public List<LoanDTO> listLoans() {

        return loanRepository.findAll()
                .stream()
                .map(LoanDTO::new)
                .toList();
    }

    // Listar EMPRÉSTIMOS por ID do GAME
    public List<LoanDTO> listLoansByGameId(Long id) {

        List<Loan> loans = loanRepository.findByGame_IdGame(id);

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Game no id: " + id);
        }
        return loans.stream().map(LoanDTO::new).toList();
    }

    // Listar EMPRÉSTIMOS por ID do USER
    public List<LoanDTO> listLoansByUserId(Long id) {

        List<Loan> loans = loanRepository.findByUser_IdUser(id);

        if (loans.isEmpty()) {
            throw new RuntimeException("Nenhum Empréstimo encontrado para o Usuário no id: " + id);
        }
        return loans.stream().map(LoanDTO::new).toList();
    }

    // Listar EMPRÉSTIMOS por DATA do EMPRÉSTIMO
    public List<LoanDTO> listLoansByLoanDate(LocalDate loanDate) {

        return loanRepository.findByLoanDate(loanDate)
                .stream()
                .map(LoanDTO::new)
                .toList();
    }

    // Listar EMPRÉSTIMOS por DATA de DEVOLUÇÃO
    public List<LoanDTO> listLoansByReturnDate(LocalDate returnDate) {

        return loanRepository.findByReturnDate(returnDate)
                .stream()
                .map(LoanDTO::new)
                .toList();
    }

    // Listar EMPRÉSTIMOS por STATUS
    public List<LoanDTO> listLoansByStatus(LoanStatus loanStatus) {

        return loanRepository.findByStatus(loanStatus)
                .stream()
                .map(LoanDTO::new)
                .toList();
    }

    // Listar EMPRÉSTIMOS por USERNAME (Nome de Usuário)
    public List<LoanDTO> listLoansByUserName(String userName) {

        return loanRepository.findByUser_Name(userName)
                .stream()
                .map(LoanDTO::new)
                .toList();
    }

    // Listar EMPRÉSTIMOS por GAME TITLE (Título do Game)
    public List<LoanDTO> listLoansByGameTitle(String gameTitle) {

        return loanRepository.findByGame_Title(gameTitle)
                .stream()
                .map(LoanDTO::new)
                .toList();
    }


}