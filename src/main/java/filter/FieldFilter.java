package filter;

public class FieldFilter extends NonTerminalNode
{
	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}
}