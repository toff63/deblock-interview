package org.deblock.exercise.exception;

/**
 * Exception to throw when an input is invalid.
 */
public class DeblockValidationException extends RuntimeException {
    public DeblockValidationException() {
    }

    public DeblockValidationException(String message) {
        super(message);
    }

    public DeblockValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeblockValidationException(Throwable cause) {
        super(cause);
    }

    protected DeblockValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
