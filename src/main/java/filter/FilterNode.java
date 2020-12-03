package filter;


import org.w3c.dom.*;
import java.util.*;

// PropertyNotFound, MethodNotFound
import javax.el.ELException.*;

public class FilterNode
{

	private ArrayList<Filter> filters;
	private Class c;

	public FilterNode(Node root, Class paramClass)
	{
		filters = new ArrayList();
		c = paramClass;

		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if(FilterNode.isFieldTag(children.item(i)))
			{
				System.out.println("Field found");
			}

			if(FilterNode.isMethodTag(children.item(i)))
			{
				System.out.println("Method found");
			}
		}
	}

	public static FilterNode generate(Node root, Class paramClass)
	{
		return new FilterNode(root, paramClass);
	}

	public static ArrayList<FilterNode> parse(Node root, Class paramClass)
	{
		ArrayList<FilterNode> filters = new ArrayList();
		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
    		if(isFilterTag(children.item(i)))
    		{
    			filters.add(new FilterNode(children.item(i), paramClass));
    		}
		}

		return filters;
	}

	private static boolean isElement(Node root)
	{
		return root.getNodeType() == Node.ELEMENT_NODE;
	}

	private static boolean nameEquals(Node root, String name)
	{
		return root.getNodeName().equals(name);
	}
	private static boolean isFieldTag(Node root)
	{
		return isElement(root) && nameEquals(root, "field");
	}

	private static boolean isMethodTag(Node root)
	{
		return isElement(root) && nameEquals(root, "method");
	}

	private static boolean isFilterTag(Node root)
	{
		return isElement(root) && nameEquals(root, "filter");
	}
}