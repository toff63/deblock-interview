package org.deblock.exercise.exception;

/**
 * Exception thrown when a call to a supplier fails
 */
public class SupplierException extends RuntimeException {
    public SupplierException() {
    }

    public SupplierException(String message) {
        super(message);
    }

    public SupplierException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupplierException(Throwable cause) {
        super(cause);
    }

    protected SupplierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
