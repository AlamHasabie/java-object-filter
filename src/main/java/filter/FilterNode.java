package filter;

import org.wc3.dom.*;
import java.util.ArrayList;

public class FilterNode
{
	private ArrayList<IFilter> filters;

	public FilterNode()
	{
		filters = new ArrayList<>();
	}

	public static FilterNode generate(Node root)
		throws ParsingException
	{

	}

	public boolean add(IFilter filter)
	{
		filters.add(filter);
	}

	public boolean shouldFilter(Object o, Class class)
	{
		foreach(IFilter filter : filters)
		{
			if(!filter.shouldFilter(o, class))
				return false;
		}

		return true;
	}
}