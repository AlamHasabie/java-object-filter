package filter;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.ITreeToString;
import util.PrimitiveWrapper;
import util.TagHelper;

import exceptions.InvalidTargetClassException;
import exceptions.ParsingException;
import exceptions.SyntaxException;
import exceptions.FilteringException;

/** The parent class should know how to build itself */
/** However, it's up to the subclass how to handle the filtering */
public abstract class AbstractFilter implements ITreeToString
{
	protected FilterNode filterNode;
	protected Class cin;
	protected Class cout;
	protected String value;

	public abstract boolean shouldFilter(Object o)
		throws FilteringException;


	protected AbstractFilter(Map<TagHelper.Tag, Node> map, Class cparam, Class cto)
		throws ParsingException
	{
		try {
			// Null safety
			filterNode = null;
			value = null;
			cin = cparam;
			cout = cto;

			if(cout.isPrimitive())
			{
				cout = PrimitiveWrapper.getWrapper(cout);
			}

			if(map.containsKey(TagHelper.Tag.CLASS))
			{
				Class ctarget = Class.forName(map.get(TagHelper.Tag.CLASS).getTextContent());
				if(!cout.isAssignableFrom(ctarget))
				{
					throw new InvalidTargetClassException(ctarget, cout);
				}

				cout = ctarget;
			}

			if(map.containsKey(TagHelper.Tag.FILTER))
			{
				filterNode = FilterNode.generate(map.get(TagHelper.Tag.FILTER), cout);
			} else 
			{
				if(map.containsKey(TagHelper.Tag.VALUE))
				{
					// Ensure toString method exists for this class
					cout.getMethod("toString");
					value = map.get(TagHelper.Tag.VALUE).getTextContent();
				}
			}
		} catch (ClassNotFoundException e)
		{
			throw new ParsingException(e);
		} catch (NoSuchMethodException e)
		{
			throw new ParsingException(e);
		}
	}
	
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
			|| nodeMap.containsKey(TagHelper.Tag.CLASS)
		))
		{
			throw new SyntaxException("Missing filter, value, or class tag");
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