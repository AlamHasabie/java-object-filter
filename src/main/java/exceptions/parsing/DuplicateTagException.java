package exceptions.parsing;

public class DuplicateTagException extends ParsingException {
    public DuplicateTagException(String errorMessage) {
        super(errorMessage);
    }
}