package com.g5.services.validators;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Account;
import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class AccountValidator implements AccountValidatorLocal {

    @Override
    public void isOpen(final Account account) {
        if (!account.isOpen()) {
            throw new IllegalStateException("The account is closed.");
        }
    }

    @Override
    public void isClosed(final Account account) {
        if (account.isOpen()) {
            throw new IllegalStateException("The account is open.");
        }
    }

    @Override
    public void hasEnoughFunds(final Account account, final BigDecimal value) throws NotEnoughFundsException {
        if (account.getBalance().compareTo(value) == -1) {
            throw new NotEnoughFundsException();
        }
    }

    @Override
    public void exists(final Account account) {
        if (account == null || account.getId() <= 0) {
            throw new IllegalArgumentException("The account does not exist.");
        }
    }

}
