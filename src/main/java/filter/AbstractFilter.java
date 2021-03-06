package filter;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.ITreeToString;
import util.TagHelper;
import exceptions.ParsingException;
import exceptions.SyntaxException;
import exceptions.FilteringException;


public abstract class AbstractFilter implements ITreeToString
{
	protected FilterNode filterNode;
	protected Class c;
	protected boolean isLeaf;
	protected String value;

	public abstract boolean shouldFilter(Object o)
		throws FilteringException;

	public static Map<TagHelper.Tag, Node> buildTagMap(Node root)
		throws ParsingException
	{
		Map<TagHelper.Tag, Node> nodeMap = new HashMap();
		NodeList nodeList = root.getChildNodes();
		for(int i = 0 ; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if(!TagHelper.isElement(node))
			{
				continue;
			}

			TagHelper.Tag currentTag = TagHelper.fromString(node.getNodeName());
			if(nodeMap.containsKey(currentTag))
			{
				throw new SyntaxException(
					"Duplicated tag " + currentTag.val + " found" 
				);
			}

			switch(currentTag)
			{
				case NAME :
					nodeMap.put(TagHelper.Tag.NAME, node);
					break;

				case VALUE :
					if(nodeMap.containsKey(TagHelper.Tag.FILTER))
					{
						throw new SyntaxException(
							"FILTER and VALUE cannot coexist"
						);
					}
					nodeMap.put(TagHelper.Tag.VALUE, node);
					break;

				case FILTER :
					if(nodeMap.containsKey(TagHelper.Tag.VALUE))
					{
						throw new SyntaxException(
							"FILTER and VALUE cannot coexist"
						);
					}
					nodeMap.put(TagHelper.Tag.FILTER, node);
					break;

				case CLASS :
					nodeMap.put(TagHelper.Tag.CLASS, node);
					break;

				default :
					throw new SyntaxException(
						"Invalid tag " + root.getNodeName() + " found"
					);
			}
		}

		if(!nodeMap.containsKey(TagHelper.Tag.NAME))
		{
			throw new SyntaxException("Missing name tag");
		}

		if(!(
			nodeMap.containsKey(TagHelper.Tag.FILTER)
			|| nodeMap.containsKey(TagHelper.Tag.VALUE)
		))
		{
			throw new SyntaxException("Missing return (filter or value) tag");
		}

		return nodeMap;
	}

	protected void addTreeIndent(StringBuilder s, int depth)
	{
		for(int i = 0 ; i < depth ; i ++)
		{
			s.append("  ");
		}
	}
}