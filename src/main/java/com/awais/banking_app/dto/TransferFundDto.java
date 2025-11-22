package com.awais.banking_app.dto;

public record TransferFundDto(Long fromAccountId,
                              Long toAccountId,
                              double amount) {

}
