package exceptions;

public class InvalidFilterArgumentClassException extends FilterException { 
    public InvalidFilterArgumentClassException(Class arg, Class expected) {
        super("Expected instance of " + expected + ", received " + arg);
    }
}