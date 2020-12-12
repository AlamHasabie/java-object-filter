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

	public FieldFilter(Map<TagHelper.Tag, Node> map, Class cparam, Field f)
		throws ParsingException
	{
		super(map, cparam, f.getType());
		field = f;
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

			if(!cin.isInstance(o))
			{
				return false;
			}

			if(filterNode != null)
			{
				return filterNode.shouldFilter(field.get(o));
			}

			if(value != null)
			{
				return field.get(o).toString().equals(value);
			}

			return cout.isInstance(field.get(o));

		} catch (IllegalAccessException e)
		{
			throw new FilteringException(e);
		}
	}

	public void toString(StringBuilder builder, int depth)
	{
		addTreeIndent(builder, depth);
		if(filterNode==null)
		{
			if(value==null)
			{
				builder.append("leaf-field:{" + field.toString() + "} class: " + cout);
			} else {
				builder.append("leaf-field:{" + field.toString() + "} val:" + value + "\n");
			}
			
		} else 
		{
			builder.append("field:{" + field.toString() + "}\n");
			filterNode.toString(builder, depth++);
		}
	}

	public static FieldFilter generate(Node root, Class cparam)
		throws ParsingException
	{
		try {
			Map<TagHelper.Tag, Node> map = AbstractFilter.buildTagMap(root);
			Field field = cparam.getField(map.get(TagHelper.Tag.NAME).getTextContent());
			return new FieldFilter(map, cparam, field);
		} catch (NoSuchFieldException e)
		{
			throw new ParsingException(e);
		}

	}
}