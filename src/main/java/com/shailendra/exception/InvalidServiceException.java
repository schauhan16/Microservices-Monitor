package com.shailendra.exception;

/**
 * @author Shailendra Chauhan
 */
public class InvalidServiceException extends RuntimeException {


    public InvalidServiceException() {
    }

    public InvalidServiceException(String message) {
        super(message);
    }
}
