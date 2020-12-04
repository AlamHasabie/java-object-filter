package filter;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MethodFilter extends Filter
{
	public MethodFilter(Node root, Class cparam)
	{

	}
	@Override
	public boolean shouldFilter(Object o, Class cparam)
	{
		return true;
	}

	public void toString(StringBuilder builder, int depth)
	{
	}


	public static MethodFilter generate(Node root, Class cparam)
	{
		return new MethodFilter(root, cparam);
	}
}