package com.awais.banking_app.Repository;

import com.awais.banking_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account, Long> {

}
