import org.w3c.dom.*;
import conf.Loader;
import filter.FilterNode;
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
			Class target = A.class;
			Node root = doc.getDocumentElement();
			ArrayList<FilterNode> filters = FilterNode.parse(doc, target);


			// Driver object 
			A a  = new A();
			a.b = new B<C>();
			a.b.t = new C(1);

			for(FilterNode filter : filters)
			{
				System.out.println(filter.toString());
				System.out.println(filter.shouldFilter(a));
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}