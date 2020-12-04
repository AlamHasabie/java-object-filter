package filter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.TagHelper;
import exceptions.*;


public class MethodFilter extends Filter
{
	private Method method;

	public MethodFilter(Node root, Class cparam)
		throws NoSuchMethodException, NoSuchFieldException
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
		throws NoSuchMethodException, NoSuchFieldException
	{
		method = cparam.getMethod(map.get(TagHelper.Tag.NAME));
		if(isLeaf && map.containsKey(TagHelper.Tag.VALUE))
		{
			value = map.get(TagHelper.Tag.VALUE);

		} else 
		{
			Class cout = method.getReturnType();
			filterNode = FilterNode.generate(root, cout);
		}
	} 


	@Override
	public boolean shouldFilter(Object o, Class cparam)
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
			builder.append("leaf-method:{" + method.toString() + "} val:" + value + "\n");
		} else 
		{
			builder.append("method:{" + method.toString() + "}\n");
			filterNode.toString(builder, depth++);
		}

	}


	public static MethodFilter generate(Node root, Class cparam)
		throws NoSuchMethodException, NoSuchFieldException
	{
		return new MethodFilter(root, cparam);
	}
}