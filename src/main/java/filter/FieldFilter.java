package filter;

import java.lang.ClassNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.TagHelper;
import util.PrimitiveWrapper;

import exceptions.FilteringException;
import exceptions.InvalidTargetClassException;
import exceptions.ParsingException;

public class FieldFilter extends AbstractFilter
{
	private Field field;

	public FieldFilter(Map<TagHelper.Tag, Node> map, Class cparam)
		throws ParsingException
	{
		try {
			c = cparam;
			field = cparam.getField(map.get(TagHelper.Tag.NAME).getTextContent());
			Class cout = field.getType();

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

			if(map.containsKey(TagHelper.Tag.VALUE))
			{
				isLeaf = true;
				cout.getMethod("toString");
				value = map.get(TagHelper.Tag.VALUE).getTextContent();
			} else 
			{
				isLeaf = false;
				filterNode = FilterNode.generate(map.get(TagHelper.Tag.FILTER), cout);
			}

		} catch (ClassNotFoundException e)
		{
			throw new ParsingException(e);

		} catch (NoSuchMethodException e)
		{
			throw new ParsingException(e);

		} catch (NoSuchFieldException e)
		{
			throw new ParsingException(e);
		}
	}

	@Override
	public boolean shouldFilter(Object o)
		throws FilteringException
	{
		try {
			if(o==null)
			{
				return false;
			}

			if(!c.isInstance(o))
			{
				return false;
			}

			if(isLeaf)
			{
				return field.get(o).toString().equals(value);
			} else 
			{
				return filterNode.shouldFilter(field.get(o));
			}
		} catch (IllegalAccessException e)
		{
			throw new FilteringException(e);
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
		throws ParsingException
	{
		return new FieldFilter(AbstractFilter.buildTagMap(root), cparam);
	}
}