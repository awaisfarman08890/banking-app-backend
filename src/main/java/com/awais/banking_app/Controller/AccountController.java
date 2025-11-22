package com.awais.banking_app.Controller;

import com.awais.banking_app.Service.AccountService;
import com.awais.banking_app.dto.Accountdto;
import com.awais.banking_app.dto.TransactionDto;
import com.awais.banking_app.dto.TransferFundDto;
import com.awais.banking_app.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
 //Add Account RestApi
    @PostMapping
    public ResponseEntity<Accountdto> createAccount(@RequestBody Accountdto accountdto){
        return new ResponseEntity<>(accountService.createAccount(accountdto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Accountdto> getAccountById(@PathVariable Long id){
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);

    }
    //deposit
    @PutMapping("/{id}/deposit")
    public ResponseEntity<Accountdto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){
        Double account = request.get("amount");
        Accountdto accountdto = accountService.deposit(id, account);
        return ResponseEntity.ok(accountdto);

    }
    //withdraw
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Accountdto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        Accountdto accountdto = accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountdto);
    }
    @GetMapping
    public ResponseEntity<List<Accountdto>> getAllAccounts(){
        List<Accountdto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }
    // Build transfer REST API
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody TransferFundDto transferFundDto){
        accountService.transferFund(transferFundDto);
        return ResponseEntity.ok("Transfer Successful");
    }
    // Build transactions REST API
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> fetchAccountTransferFunds(@PathVariable("id") Long accountId){
     List<TransactionDto> transactions = accountService.getAccountTransactions(accountId);
     return ResponseEntity.ok(transactions);
    }


}
