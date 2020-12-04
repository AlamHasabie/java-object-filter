package filter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.TagHelper;
import exceptions.*;

public class FieldFilter extends Filter
{
	private Field field;

	public FieldFilter(Node root, Class cparam)
		throws NoSuchFieldException
	{
		c = cparam;
		Map<TagHelper.Tag, String> foundTags = new HashMap();
		NodeList nodeList = root.getChildNodes();
		Node childFilterNode = root;

		for(int i = 0 ; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if(!TagHelper.isElement(node))
			{
				continue;
			}

			TagHelper.Tag currentTag = TagHelper.fromString(node.getNodeName());
			if(foundTags.containsKey(currentTag))
			{
				throw new DuplicateTagException(
					"Duplicated tag " + currentTag.val + " found" 
				);
			}

			switch(currentTag)
			{
				case NAME :
					foundTags.put(TagHelper.Tag.NAME, node.getTextContent());
					break;

				case VALUE :
					if(foundTags.containsKey(TagHelper.Tag.FILTER))
					{
						throw new TagConflictException(
							"FILTER and VALUE cannot coexist"
						);
					}

					foundTags.put(TagHelper.Tag.VALUE, node.getTextContent());
					isLeaf = true;

					break;

				case FILTER :
					if(foundTags.containsKey(TagHelper.Tag.VALUE))
					{
						throw new TagConflictException(
							"FILTER and VALUE cannot coexist"
						);
					}

					foundTags.put(TagHelper.Tag.FILTER, null);
					isLeaf = false;
					childFilterNode = node;
					break;

				default :
					throw new InvalidTagException(
						"Invalid tag " + root.getNodeName() + " found"
					);

			}
		}

		if(!foundTags.containsKey(TagHelper.Tag.NAME))
		{
			throw new MissingNameException("Missing name tag");
		}

		if(!(
			foundTags.containsKey(TagHelper.Tag.FILTER)
			|| foundTags.containsKey(TagHelper.Tag.VALUE)
		))
		{
			throw new MissingReturnException("Missing filter | value tag");
		}

		build(foundTags, childFilterNode, cparam);
	}

	private void build(Map<TagHelper.Tag, String> map, Node root, Class cparam)
		throws NoSuchFieldException
	{
		field = cparam.getField(map.get(TagHelper.Tag.NAME));
		if(isLeaf && map.containsKey(TagHelper.Tag.VALUE))
		{
			value = map.get(TagHelper.Tag.VALUE);

		} else 
		{
			Class cout = field.getType();
			filterNode = FilterNode.generate(root, cout);
		}
	} 

	@Override
	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}

	public void toString(StringBuilder builder, int depth)
	{
		for(int i = 0 ; i < depth ; i ++)
		{
			builder.append("  ");
		}

		if(isLeaf)
		{
			builder.append("leaf-field:{" + field.toString() + "} val:" + value + "\n");
		} else 
		{
			builder.append("field:{" + field.toString() + "}\n");
			filterNode.toString(builder, depth++);
		}

	}


	public static FieldFilter generate(Node root, Class c)
		throws NoSuchFieldException
	{
		return new FieldFilter(root, c);
	}
}