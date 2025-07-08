package dev.viniciussr.gameslibrary.exception.loan;

import dev.viniciussr.gameslibrary.exception.BusinessException;

public class LoanAlreadyReturnedException extends BusinessException {
    public LoanAlreadyReturnedException(String message) {
        super(message);
    }
}
