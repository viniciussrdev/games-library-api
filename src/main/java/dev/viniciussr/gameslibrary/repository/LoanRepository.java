package dev.viniciussr.gameslibrary.repository;

import dev.viniciussr.gameslibrary.enums.LoanStatus;
import dev.viniciussr.gameslibrary.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByGame_IdGame(Long IdGame);

    List<Loan> findByUser_IdUser(Long IdUser);

    List<Loan> findByLoanDate(LocalDate loanDate);

    List<Loan> findByReturnDate(LocalDate returnDate);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByUser_Name(String userName);

    List<Loan> findByGame_Title(String gameTitle);
}
