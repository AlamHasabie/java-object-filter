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

	public FieldFilter(Map<TagHelper.Tag, Node> map, Class cparam)
		throws NoSuchMethodException, NoSuchFieldException
	{
		c = cparam;
		field = cparam.getField(map.get(TagHelper.Tag.NAME).getTextContent());
		if(map.containsKey(TagHelper.Tag.VALUE))
		{
			isLeaf = true;
			value = map.get(TagHelper.Tag.VALUE).getTextContent();
		} else 
		{
			isLeaf = false;
			Class cout = field.getType();
			filterNode = FilterNode.generate(map.get(TagHelper.Tag.FILTER), cout);
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
			builder.append("leaf-field:{" + field.toString() + "} val:" + value + "\n");
		} else 
		{
			builder.append("field:{" + field.toString() + "}\n");
			filterNode.toString(builder, depth++);
		}
	}

	public static FieldFilter generate(Node root, Class cparam)
		throws NoSuchMethodException, NoSuchFieldException
	{
		return new FieldFilter(Filter.buildTagMap(root), cparam);
	}
}