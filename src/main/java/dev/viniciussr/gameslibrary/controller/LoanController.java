package dev.viniciussr.gameslibrary.controller;

import dev.viniciussr.gameslibrary.dto.LoanDTO;
import dev.viniciussr.gameslibrary.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

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

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findLoanById(@PathVariable Long id) {
        return loanService.findLoanById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> listLoans() {
        return ResponseEntity.ok(loanService.listLoans());
    }







}
