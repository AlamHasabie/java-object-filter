package utils;

import java.util.Map;
import java.util.HashMap;

public class PrimitiveWrapper
{

	private final static Map<Class<?>, Class<?>> primitiveToWrapper = new HashMap();

	static 
	{
		primitiveToWrapper.put(boolean.class, Boolean.class);
		primitiveToWrapper.put(byte.class, Byte.class);
		primitiveToWrapper.put(short.class, Short.class);
		primitiveToWrapper.put(char.class, Character.class);
		primitiveToWrapper.put(int.class, Integer.class);
		primitiveToWrapper.put(long.class, Long.class);
		primitiveToWrapper.put(float.class, Float.class);
		primitiveToWrapper.put(double.class, Double.class);
	}


	public static Class getWrapper(Class c)
	{
		return primitiveToWrapper.get(c);
	}

}