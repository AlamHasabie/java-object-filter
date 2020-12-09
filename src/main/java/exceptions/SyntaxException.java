package exceptions;

public class SyntaxException extends ParsingException { 
    public SyntaxException(String errorMessage) {
        super(errorMessage);
    }
}