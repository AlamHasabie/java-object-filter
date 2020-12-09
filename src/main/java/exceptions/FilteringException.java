package exceptions;

public class FilteringException extends Exception { 
	public FilteringException(Throwable cause)
	{
		super(cause);
	}
	
    public FilteringException(String errorMessage)
    {
        super(errorMessage);
    }
}