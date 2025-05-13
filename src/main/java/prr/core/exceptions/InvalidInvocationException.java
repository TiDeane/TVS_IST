package prr.core.exceptions;

/**
 * Thrown when a method is called in a way that violates the allowed state or context.
 */
public class InvalidInvocationException extends RuntimeException {

    public InvalidInvocationException() {
        super();
    }

    public InvalidInvocationException(String message) {
        super(message);
    }
}
