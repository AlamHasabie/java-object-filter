package exceptions;

public class MissingNameException extends FilterException { 
    public MissingNameException(String errorMessage) {
        super(errorMessage);
    }
}