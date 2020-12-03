package filter;

public class MethodFilter extends Filter
{
	@Override
	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}
}