package com.cezar.HotelBackend.service.exception;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;


public class UsernameAlreadyExistsException extends Exception {
    public UsernameAlreadyExistsException(){
        super("Username already exists");
    }
}
