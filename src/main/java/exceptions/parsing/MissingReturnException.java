package exceptions.parsing;

public class MissingReturnException extends ParsingException { 
    public MissingReturnException(String errorMessage) {
        super(errorMessage);
    }
}