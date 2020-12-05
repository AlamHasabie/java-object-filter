package filter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.TagHelper;
import exceptions.*;

public class FieldFilter extends Filter
{
	private Field field;
	private Method toString;

	public FieldFilter(Map<TagHelper.Tag, Node> map, Class cparam)
		throws NoSuchMethodException, NoSuchFieldException
	{
		c = cparam;
		field = cparam.getField(map.get(TagHelper.Tag.NAME).getTextContent());
		Class cout = field.getType();
		if(map.containsKey(TagHelper.Tag.VALUE))
		{
			isLeaf = true;
			toString = cout.getMethod("toString");
			value = map.get(TagHelper.Tag.VALUE).getTextContent();
		} else 
		{
			isLeaf = false;
			filterNode = FilterNode.generate(map.get(TagHelper.Tag.FILTER), cout);
		}
	}

	@Override
	public boolean shouldFilter(Object o)
		throws IllegalAccessException, InvocationTargetException
	{
		if(!c.isInstance(o))
		{
			throw new InvalidFilterArgumentClassException(o.getClass(), c);
		}

		if(isLeaf)
		{
			return field.get(o).toString().equals(value);
		} else 
		{
			return filterNode.shouldFilter(field.get(o));
		}
	}

	public void toString(StringBuilder builder, int depth)
	{
		addTreeIndent(builder, depth);
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