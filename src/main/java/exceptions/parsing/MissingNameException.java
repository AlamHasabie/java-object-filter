package exceptions.parsing;

public class MissingNameException extends ParsingException { 
    public MissingNameException(String errorMessage) {
        super(errorMessage);
    }
}