package exceptions;

public class FilterException extends RuntimeException { 
    public FilterException(String errorMessage) {
        super(errorMessage);
    }
}