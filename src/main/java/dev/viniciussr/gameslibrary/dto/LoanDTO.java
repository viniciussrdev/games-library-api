package dev.viniciussr.gameslibrary.dto;

import dev.viniciussr.gameslibrary.enums.LoanStatus;
import dev.viniciussr.gameslibrary.model.Loan;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record LoanDTO(

        Long idLoan,

        @NotNull(message = "ID DO GAME é campo obrigatório")
        Long gameId,

        @NotNull(message = "ID DO USUÁRIO é campo obrigatório")
        Long userId,

        @NotNull(message = "DATA DO EMPRÉSTIMO é campo obrigatório")
        @PastOrPresent(message = "DATA DO EMPRÉSTIMO deve ser hoje ou passado")
        LocalDate loanDate,

        @NotNull(message = "DATA DE DEVOLUÇÃO é campo obrigatório")
        @FutureOrPresent(message = "DATA DE DEVOLUÇÃO deve ser hoje ou futuro")
        LocalDate returnDate,

        @NotNull(message = "STATUS DO EMPRÉSTIMO é campo obrigatório")
        @Pattern(regexp = "ACTIVE|RETURNED|LATE",
                message = "STATUS DO EMPRÉSTIMO deve ser um entre ACTIVE - RETURNED - LATE")
        LoanStatus status
) {
    public LoanDTO(Loan loan) {
        this(
                loan.getIdLoan(),
                loan.getGame().getIdGame(),
                loan.getUser().getIdUser(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }
}
