package exceptions.filtering;

public class InvalidFilterArgumentClassException extends FilteringException { 
    public InvalidFilterArgumentClassException(Class arg, Class expected) {
        super("Expected instance of " + expected + ", received " + arg);
    }
}