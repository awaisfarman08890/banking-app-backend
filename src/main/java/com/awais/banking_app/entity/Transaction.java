package com.awais.banking_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private double amount;
    private String transactionType; //DEPOSIT, WITHDRAW. TRANSFER
    private LocalDateTime transactionDate;
}
