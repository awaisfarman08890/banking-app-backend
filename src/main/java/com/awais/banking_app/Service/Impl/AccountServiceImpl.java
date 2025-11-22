package com.awais.banking_app.Service.Impl;

import com.awais.banking_app.Repository.AccountRepository;
import com.awais.banking_app.Repository.TransactionRepository;
import com.awais.banking_app.Service.AccountService;
import com.awais.banking_app.dto.Accountdto;
import com.awais.banking_app.dto.TransactionDto;
import com.awais.banking_app.dto.TransferFundDto;
import com.awais.banking_app.entity.Account;
import com.awais.banking_app.entity.Transaction;
import com.awais.banking_app.exception.AccountException;
import com.awais.banking_app.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    private  static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
    private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";
    @Override
    public Accountdto createAccount(Accountdto accountdto) {
        Account account = AccountMapper.mapToaccount(accountdto);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountdto(savedAccount);
    }
    @Override
    public Accountdto getAccountById(Long id){
       Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account not found"));
       return AccountMapper.mapToAccountdto(account);
    }

    @Override
    public Accountdto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account not found"));
        double total = account.getAccountBalance() + amount;
        account.setAccountBalance(total);
        Account savedAccount = accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        return AccountMapper.mapToAccountdto(savedAccount);
    }

    @Override
    public Accountdto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account not found"));
        if(account.getAccountBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }
        double total = account.getAccountBalance() - amount;
        account.setAccountBalance(total);
        Account savedAccount = accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
        return AccountMapper.mapToAccountdto(savedAccount);
    }

    @Override
    public List<Accountdto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountdto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new AccountException("Account not found"));
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFund(TransferFundDto transferFundDto) {
        // Retrieve the account from which we send the amount
        Account fromaccount = accountRepository
                .findById(transferFundDto.fromAccountId())
                .orElseThrow(()-> new AccountException("Account does not exists"));
        //Retrieve the account to which we send the amount
        Account toAccount = accountRepository.findById(transferFundDto.toAccountId())
                        .orElseThrow(()-> new AccountException("Account does not exists"));
        if(fromaccount.getAccountBalance() < transferFundDto.amount()){
            throw new AccountException("Insufficient balance");
        }
        // Debit the amount fromAccount object
        fromaccount.setAccountBalance(fromaccount.getAccountBalance() - transferFundDto.amount());
        //Credit the amount to Account object
        toAccount.setAccountBalance(toAccount.getAccountBalance() + transferFundDto.amount());
        accountRepository.save(fromaccount);
        accountRepository.save(toAccount);
        Transaction transaction = new Transaction();
        transaction.setAccountId(transferFundDto.fromAccountId());
        transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
        return transactions.stream().map((this::convertEntityToTransactionDto)).collect(Collectors.toList());

    }
    private TransactionDto convertEntityToTransactionDto(Transaction transaction) {
        return  new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTransactionDate()
        );
    }
}
