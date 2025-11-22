package com.awais.banking_app.Repository;

import com.awais.banking_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);
}

