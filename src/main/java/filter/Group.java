package filter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exceptions.FilteringException;
import exceptions.InvalidTargetClassException;
import exceptions.ParsingException;
import exceptions.SyntaxException;

import utils.TagHelper;
import utils.ITreeToString;

public class Group implements ITreeToString
{
	public List<FilterNode> filters;
	public Class c;

	public Group(Node root)
		throws ParsingException
	{
		c = Group.getClassFromGroupTag(root);
		filters = new ArrayList();

		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
    		if(TagHelper.isElement(child))
    		{
    			if (!TagHelper.tagEquals(child, TagHelper.Tag.FILTER))
    			{
    				throw new SyntaxException(
    					"Invalid tag " + child.getNodeName() + " in group parsing"
    				);
    			}

				filters.add(new FilterNode(child, c));
    		}
		}
	}

	public boolean shouldFilter(Object o)
		throws FilteringException
	{
		for(FilterNode filter : filters)
		{
			if(filter.shouldFilter(o))
			{
				return true;
			}
		}
		return false;
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

		builder.append("<group>, target:{" + c + "}\n");
		for(FilterNode filter: filters)
		{
			filter.toString(builder, depth + 1);
		}
	}

	public static Class getClassFromGroupTag(Node root)
		throws ParsingException
	{
		try {
			NamedNodeMap map = root.getAttributes();
			if(map == null)
			{
				throw new SyntaxException("<group> tag has no \"target\" attribute");
			}

			Node n = map.getNamedItem("target");
			if(n == null)
			{
				throw new SyntaxException("<group> tag has no \"target\" attribute");
			}

			return Class.forName(n.getNodeValue());

		} catch (ClassNotFoundException e)
		{
			throw new InvalidTargetClassException(e);	
		}

	}
}