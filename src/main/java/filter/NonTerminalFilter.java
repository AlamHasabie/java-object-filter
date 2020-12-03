package filter;

public abstract class NonTerminalFilter implements IFilter
{
	private FilterNode node;

	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}
}