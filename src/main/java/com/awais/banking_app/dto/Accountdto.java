package com.awais.banking_app.dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//public class Accountdto {
//    private Long id;
//    private String accountHolderName;
//    private double accountBalance;
//}

public record Accountdto(
        Long id,
        String accountHolderName,
        double accountBalance
) {
}