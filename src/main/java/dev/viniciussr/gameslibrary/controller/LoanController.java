package dev.viniciussr.gameslibrary.controller;

import dev.viniciussr.gameslibrary.dto.GameDTO;
import dev.viniciussr.gameslibrary.dto.LoanDTO;
import dev.viniciussr.gameslibrary.enums.LoanStatus;
import dev.viniciussr.gameslibrary.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @RequestBody LoanDTO dto) {
        return ResponseEntity.ok(loanService.updateLoan(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLoan(@RequestParam Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<Void> returnLoan(@PathVariable Long id) {
        loanService.returnLoan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.findLoanById(id));
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> listLoans() {
        return ResponseEntity.ok(loanService.listLoans());
    }

    @GetMapping("/game-id/{id}")
    public ResponseEntity<List<LoanDTO>> listLoansByGameId(@RequestParam Long IdGame) {
        return ResponseEntity.ok(loanService.listLoansByGameId(IdGame));
    }

    @GetMapping("/user-id/{id}")
    public ResponseEntity<List<LoanDTO>> listLoansByUserId(@RequestParam Long IdUser) {
        return ResponseEntity.ok(loanService.listLoansByUserId(IdUser));
    }

    @GetMapping("/loan-date")
    public ResponseEntity<List<LoanDTO>> listLoansByLoanDate(@RequestParam LocalDate loanDate) {
        return ResponseEntity.ok(loanService.listLoansByLoanDate(loanDate));
    }

    @GetMapping("/return-date")
    public ResponseEntity<List<LoanDTO>> listLoansByReturnDate(@RequestParam LocalDate returnDate) {
        return ResponseEntity.ok(loanService.listLoansByReturnDate(returnDate));
    }

    @GetMapping("/status")
    public ResponseEntity<List<LoanDTO>> listLoansByStatus(@RequestParam LoanStatus loanStatus) {
        return ResponseEntity.ok(loanService.listLoansByStatus(loanStatus));
    }

    @GetMapping("/username")
    public ResponseEntity<List<LoanDTO>> listLoansByUserName(@RequestParam String userName) {
        return ResponseEntity.ok(loanService.listLoansByUserName(userName));
    }

    @GetMapping("/game-title")
    public ResponseEntity<List<LoanDTO>> listLoansByGameTitle(@RequestParam String gameTitle) {
        return ResponseEntity.ok(loanService.listLoansByGameTitle(gameTitle));
    }
}
