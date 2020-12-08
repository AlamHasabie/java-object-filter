package exceptions.parsing;

public class ParsingException extends Exception { 
    public ParsingException(String errorMessage) {
        super(errorMessage);
    }
}