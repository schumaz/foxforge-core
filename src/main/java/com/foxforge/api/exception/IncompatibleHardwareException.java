package com.foxforge.api.exception;

/**
 * Exception thrown when a hardware configuration fails one or more compatibility specifications.
 */
public class IncompatibleHardwareException extends RuntimeException {

    public IncompatibleHardwareException(String message) {
        super(message);
    }
}
