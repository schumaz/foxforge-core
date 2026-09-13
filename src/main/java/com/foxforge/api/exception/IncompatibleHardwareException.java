package com.foxforge.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a hardware compatibility rule is violated during PC build validation.
 * Automatically maps to HTTP 400 (Bad Request) if uncaught by a dedicated controller advice.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncompatibleHardwareException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new IncompatibleHardwareException with the specified detail message.
     *
     * @param message the detail message explaining which compatibility rule failed
     */
    public IncompatibleHardwareException(String message) {
        super(message);
    }

    /**
     * Constructs a new IncompatibleHardwareException with the specified detail message and cause.
     *
     * @param message the detail message explaining which compatibility rule failed
     * @param cause the underlying cause of the exception
     */
    public IncompatibleHardwareException(String message, Throwable cause) {
        super(message, cause);
    }
}
