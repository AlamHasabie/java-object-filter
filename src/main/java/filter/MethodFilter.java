package filter;

import java.lang.ClassNotFoundException;
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

public class MethodFilter extends AbstractFilter
{
	private Method method;

	public MethodFilter(Map<TagHelper.Tag, Node> map, Class cparam, Method m)
		throws ParsingException
	{
		super(map, cparam, m.getReturnType());
		method = m;
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
				return method.invoke(o).toString().equals(value);
			}

			if(value != null)
			{
				return method.invoke(o).toString().equals(value);
			}

			return cout.isInstance(method.invoke(o));
		
		} catch (IllegalAccessException e)
		{
			throw new FilteringException(e);
		} catch (InvocationTargetException e)
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
				builder.append("leaf-method:{" + method.toString() + "} class: " + cout);
			} else {
				builder.append("leaf-method:{" + method.toString() + "} val:" + value + "\n");
			}

		} else 
		{
			builder.append("method:{" + method.toString() + "}\n");
			filterNode.toString(builder, depth++);
		}
	}

	public static MethodFilter generate(Node root, Class cparam)
		throws ParsingException
	{
		try {
			Map<TagHelper.Tag, Node> map = AbstractFilter.buildTagMap(root);
			Method method = cparam.getMethod(map.get(TagHelper.Tag.NAME).getTextContent());
			return new MethodFilter(map, cparam, method);
		} catch (NoSuchMethodException e)
		{
			throw new ParsingException(e);
		}
	}
}