package prr.core.exceptions;

public class InvalidOperationException extends RuntimeException {

  public InvalidOperationException() {
        super();
    }

    public InvalidOperationException(String message) {
        super(message);
    }
  
}
