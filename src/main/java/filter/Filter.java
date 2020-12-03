package filter;

public abstract class Filter
{
	private FilterNode node;
	public abstract boolean shouldFilter(Object o, Class c);
}