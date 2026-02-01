package exceptions;

public class Errors extends RuntimeException {

    public enum ErrorType {
        AUTOMATION,
        DATA_ISSUE
    }

    private final ErrorType errorType;

    public Errors(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public Errors(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
