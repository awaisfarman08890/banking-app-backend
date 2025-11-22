package com.awais.banking_app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class AccountException extends RuntimeException{
    public AccountException(String message) {
        super(message);
    }
}
