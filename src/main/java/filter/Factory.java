package filter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exceptions.ParsingException;
import exceptions.SyntaxException;
import utils.TagHelper;
import utils.TagHelper.Tag;

public class Factory
{
	public static List<Group> parse(Node root)
		throws ParsingException
	{
		List<Group> groups = new ArrayList();
		NodeList children = root.getChildNodes();

		for(int i = 0 ; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if(TagHelper.isElement(child))
			{
				if(!TagHelper.tagEquals(child, Tag.GROUP))
				{
					throw new SyntaxException("Expecting <group> tag");
				}
				
				groups.add(new Group(child));
			}
		}

		return groups;
	}
}
