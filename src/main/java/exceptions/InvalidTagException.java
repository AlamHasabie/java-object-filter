package exceptions;

public class InvalidTagException extends FilterException { 
    public InvalidTagException(String errorMessage) {
        super(errorMessage);
    }
}