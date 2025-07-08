package dev.viniciussr.gameslibrary.exception.loan;

import dev.viniciussr.gameslibrary.exception.BusinessException;

public class LoanNotFoundException extends BusinessException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
