import org.w3c.dom.*;
import filter.Factory;
import filter.FilterNode;
import filter.Group;
import java.lang.reflect.*;
import sampleclass.*;
import java.util.*;

public class Main
{
	public static void main(String[] args) 
	{
		try 
		{

			List<Group> groups = Factory.generate();

			// Driver object 
			A a  = new A();

			for(Group group : groups)
			{
				System.out.println(group.toString());
				System.out.println(group.shouldFilter(a));
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}