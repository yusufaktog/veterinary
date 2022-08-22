package com.aktog.yusuf.veterinary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AddressInUseException extends RuntimeException{
    public AddressInUseException(String message) {
        super(message);
    }
}
