package exceptions.parsing;

public class InvalidTargetClassException extends ParsingException { 
    public InvalidTargetClassException(String errorMessage) {
        super(errorMessage);
    }
}