package com.awais.banking_app.mapper;

import com.awais.banking_app.dto.Accountdto;
import com.awais.banking_app.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class AccountMapper {
    public static Account mapToaccount(Accountdto accountdto) {
        Account account = new Account(
                accountdto.id(),
                accountdto.accountHolderName(),
                accountdto.accountBalance()
        );
        return account;
    }
    public static Accountdto mapToAccountdto(Account account) {
        Accountdto accountdto = new Accountdto(
                account.getId(),
                account.getAccountholderName(),
                account.getAccountBalance()
        );
      return accountdto;
    }
}
