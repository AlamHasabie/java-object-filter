package exceptions;

public class ConfigurationLoadingException extends Exception {
	public ConfigurationLoadingException(Throwable cause)
	{
		super(cause);
	}
	
    public ConfigurationLoadingException(String errorMessage)
    {
        super(errorMessage);
    }
}