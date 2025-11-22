package com.awais.banking_app.Service;

import com.awais.banking_app.dto.Accountdto;
import com.awais.banking_app.dto.TransactionDto;
import com.awais.banking_app.dto.TransferFundDto;

import java.util.List;

public interface AccountService {
    Accountdto createAccount(Accountdto dto);
    Accountdto getAccountById(Long id);
    Accountdto deposit(Long id, double amount);
    Accountdto withdraw(Long id, double amount);
    List<Accountdto> getAllAccounts();
    void deleteAccount(Long id);
    void transferFund(TransferFundDto transferFundDto);
    List<TransactionDto> getAccountTransactions(Long accountId);
}
