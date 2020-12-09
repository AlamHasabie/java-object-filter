package filter;

import java.lang.ClassNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.PrimitiveWrapper;
import utils.TagHelper;
import exceptions.ParsingException;
import exceptions.InvalidTargetClassException;
import exceptions.FilteringException;

public class MethodFilter extends AbstractFilter
{
	private Method method;

	public MethodFilter(Map<TagHelper.Tag, Node> map, Class cparam)
		throws ParsingException
	{
		try {

			c = cparam;
			method = cparam.getMethod(map.get(TagHelper.Tag.NAME).getTextContent());
			Class cout = method.getReturnType();

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
		}
	}

	@Override
	public boolean shouldFilter(Object o)
		throws FilteringException
	{

		// Avoid Null Pointer exception
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
				return method.invoke(o).toString().equals(value);
			} else 
			{
				return filterNode.shouldFilter(method.invoke(o));
			}
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
		throws ParsingException
	{
		return new MethodFilter(AbstractFilter.buildTagMap(root), cparam);
	}
}