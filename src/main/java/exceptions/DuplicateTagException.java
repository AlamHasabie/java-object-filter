package exceptions;

public class DuplicateTagException extends FilterException { 
    public DuplicateTagException(String errorMessage) {
        super(errorMessage);
    }
}