package filter;

import org.w3c.dom.*;
import java.util.*;

import java.lang.reflect.InvocationTargetException;

import util.ITreeToString;
import util.TagHelper;

import exceptions.FilteringException;
import exceptions.ParsingException;
import exceptions.SyntaxException;

public class FilterNode implements ITreeToString 
{

	private ArrayList<AbstractFilter> filters;
	private Class c;

	public FilterNode(Node root, Class paramClass)
		throws ParsingException
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
					filters.add(MethodFilter.generate(node, paramClass));
				} else 
				{
					throw new SyntaxException(
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
		for(AbstractFilter filter: filters)
		{
			filter.toString(builder, depth + 1);
		}
	}

	public boolean shouldFilter(Object o)
		throws FilteringException
	{
		if(o==null)
		{
			return false;
		}

		if(!c.isInstance(o))
		{
			return false;
		}

		for(AbstractFilter filter : filters)
		{
			if(!filter.shouldFilter(o)){
				return false;
			}
		}

		return true;
	}

	public static FilterNode generate(Node root, Class paramClass)
		throws ParsingException
	{
		return new FilterNode(root, paramClass);
	}
}