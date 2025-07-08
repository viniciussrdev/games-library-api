package dev.viniciussr.gameslibrary.exception.loan;

import dev.viniciussr.gameslibrary.exception.BusinessException;
import dev.viniciussr.gameslibrary.model.User;

public class LoanLimitExceededException extends BusinessException {
    public LoanLimitExceededException(User user) {
        super("Limite de Empréstimos atingido para o Usuário no Plano: " + user.getPlan());
    }
}
