package com.g5.businesslogic.account;

import com.g5.exceptions.NotEnoughFundsException;
import com.g5.types.Account;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public interface AccountValidatorLocal {

    public void isOpen(final Account account);

    public void isClosed(final Account account);

    public void hasEnoughFunds(final Account account, final BigDecimal value)
            throws NotEnoughFundsException;

    void exists(final Account account);

}
