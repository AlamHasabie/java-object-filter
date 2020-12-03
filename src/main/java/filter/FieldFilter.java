package filter;

public class FieldFilter extends Filter
{

	private Class cin;
	private Field field;

	@Override

	public FieldFilter(Node root, Class cparam)
	{}
	
	public boolean shouldFilter(Object o, Class c)
	{
		return true;
	}


	public static FieldFilter generate(Node root, Class c)
	{


	}
}