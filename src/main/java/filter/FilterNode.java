package filter;


import org.w3c.dom.*;
import java.util.*;
import utils.TagHelper;
import utils.ITreeToString;
import exceptions.InvalidTagException;

public class FilterNode implements ITreeToString 
{

	private ArrayList<Filter> filters;
	private Class c;

	public FilterNode(Node root, Class paramClass)
		throws NoSuchFieldException
	{
		filters = new ArrayList();
		c = paramClass;

		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(TagHelper.isElement(node))
			{
				if(TagHelper.tagEquals(node, TagHelper.Tag.FIELD))
				{
					filters.add(FieldFilter.generate(node, paramClass));
				} else if (TagHelper.tagEquals(node, TagHelper.Tag.METHOD))
				{
					System.out.println("Method found");
					//filters.add(MethodFilter.generate(node, paramClass));
				} else 
				{
					throw new InvalidTagException(
						"Invalid tag " + node.getNodeName() + " during filter parsing"
					);
				}
			}
		}
	}

	public String toString()
	{
		StringBuilder strBuilder = new StringBuilder();
		toString(strBuilder, 0);
		return strBuilder.toString();
	}

	public void toString(StringBuilder builder, int depth)
	{
		for(int i = 0 ; i < depth; i++)
		{
			builder.append("  ");
		}

		builder.append("filter , class:{" + c + "}\n");
		for(Filter filter: filters)
		{
			filter.toString(builder, depth + 1);
		}
	}

	public static FilterNode generate(Node root, Class paramClass)
		throws NoSuchFieldException
	{
		return new FilterNode(root, paramClass);
	}

	public static ArrayList<FilterNode> parse(Node root, Class paramClass)
		throws NoSuchFieldException
	{
		ArrayList<FilterNode> filters = new ArrayList();
		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
    		if(TagHelper.isElement(children.item(i)))
    		{
    			if (!TagHelper.tagEquals(children.item(i), TagHelper.Tag.FILTER))
    			{
    				throw new InvalidTagException(
    					"Invalid tag " + children.item(i).getNodeName() + " found during parsing"
    				);
    			}

				filters.add(new FilterNode(children.item(i), paramClass));
    		}
		}

		return filters;
	}

}