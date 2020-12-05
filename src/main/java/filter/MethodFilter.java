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
	private Method toString;

	public MethodFilter(Map<TagHelper.Tag, Node> map, Class cparam)
		throws NoSuchMethodException, NoSuchFieldException
	{
		c = cparam;
		method = cparam.getMethod(map.get(TagHelper.Tag.NAME).getTextContent());
		Class cout = method.getReturnType();
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
	public boolean shouldFilter(Object o, Class cparam)
	{
		return true;
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
		throws NoSuchMethodException, NoSuchFieldException
	{
		return new MethodFilter(Filter.buildTagMap(root), cparam);
	}
}