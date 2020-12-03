package filter;

public class TerminalFilter implements IFilter
{
	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}
}