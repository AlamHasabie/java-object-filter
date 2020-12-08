package exceptions.parsing;

public class InvalidTargetClassException extends ParsingException { 
    public InvalidTargetClassException(Class arg, Class expected) {
        super("Class argument " + arg + " not an instance of " + expected);
    }
}