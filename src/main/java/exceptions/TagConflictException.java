package exceptions;

public class TagConflictException extends FilterException { 
    public TagConflictException(String errorMessage) {
        super(errorMessage);
    }
}