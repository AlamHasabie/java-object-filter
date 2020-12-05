package filter;

import java.util.HashMap;
import java.util.Map;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.ITreeToString;
import utils.TagHelper;
import exceptions.*;


public abstract class Filter implements ITreeToString
{
	protected FilterNode filterNode;
	protected Class c;
	protected boolean isLeaf;
	protected String value;

	public abstract boolean shouldFilter(Object o, Class cparam);

	/** Throws unchecked FilterException , which should propagate to
	{@link filter.FilterNode#parse(Node root, Class paramClass)}
	*/
	public static Map<TagHelper.Tag, Node> buildTagMap(Node root)
		throws NoSuchMethodException, NoSuchFieldException
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
				throw new DuplicateTagException(
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
						throw new TagConflictException(
							"FILTER and VALUE cannot coexist"
						);
					}
					nodeMap.put(TagHelper.Tag.VALUE, node);
					break;

				case FILTER :
					if(nodeMap.containsKey(TagHelper.Tag.VALUE))
					{
						throw new TagConflictException(
							"FILTER and VALUE cannot coexist"
						);
					}
					nodeMap.put(TagHelper.Tag.FILTER, node);
					break;

				default :
					throw new InvalidTagException(
						"Invalid tag " + root.getNodeName() + " found"
					);
			}
		}

		if(!nodeMap.containsKey(TagHelper.Tag.NAME))
		{
			throw new MissingNameException("Missing name tag");
		}

		if(!(
			nodeMap.containsKey(TagHelper.Tag.FILTER)
			|| nodeMap.containsKey(TagHelper.Tag.VALUE)
		))
		{
			throw new MissingReturnException("Missing return (filter or value) tag");
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