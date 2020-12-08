package exceptions.parsing;

public class TagConflictException extends ParsingException { 
    public TagConflictException(String errorMessage) {
        super(errorMessage);
    }
}