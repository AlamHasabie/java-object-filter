package filter;

public class MethodFilter extends NonTerminalFilter
{
	@Override
	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}
}