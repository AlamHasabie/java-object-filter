package exceptions.parsing;

public class InvalidTagException extends ParsingException { 
    public InvalidTagException(String errorMessage) {
        super(errorMessage);
    }
}