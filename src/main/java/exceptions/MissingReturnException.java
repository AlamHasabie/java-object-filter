package exceptions;

public class MissingReturnException extends FilterException { 
    public MissingReturnException(String errorMessage) {
        super(errorMessage);
    }
}