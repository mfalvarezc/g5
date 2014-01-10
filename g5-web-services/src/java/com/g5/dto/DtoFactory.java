package com.g5.dto;

import com.g5.types.Account;
import com.g5.types.Customer;
import com.g5.types.Transaction;

public class DtoFactory {

    public CustomerDto createCustomerDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setId(customer.getId());
        customerDto.setEnabled(customer.isEnabled());

        return customerDto;
    }

    public AccountDto createAccountDto(Account account) {
        if (account == null) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setCustomerId(account.getCustomer().getId());
        accountDto.setCreationDate(account.getCreationDate());
        accountDto.setBalance(account.getBalance());
        accountDto.setOpen(account.isOpen());

        return accountDto;
    }

    public TransactionDto createTransactionDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setId(transaction.getId());
        transactionDto.setAccountId(transaction.getAccount().getId());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setValue(transaction.getValue());

        return transactionDto;
    }

}
