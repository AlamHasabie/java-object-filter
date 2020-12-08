package exceptions.filtering;

public class FilteringException extends RuntimeException { 
    public FilteringException(String errorMessage) {
        super(errorMessage);
    }
}