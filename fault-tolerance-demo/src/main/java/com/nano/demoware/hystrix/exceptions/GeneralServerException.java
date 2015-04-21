package com.nano.demoware.hystrix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
// 500
public class GeneralServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GeneralServerException(String message) {
        super(message);
    }

    public GeneralServerException(String message, NumberFormatException cause) {
        super(message, cause);
    }
}
