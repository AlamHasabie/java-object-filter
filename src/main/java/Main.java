import org.w3c.dom.*;
import conf.Loader;
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
			Document doc = Loader.load("sample.xml");
			Node root = doc.getDocumentElement();

			List<Group> groups = Factory.parse(doc);

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