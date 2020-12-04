import org.w3c.dom.*;
import conf.Loader;
import filter.FilterNode;
import java.lang.reflect.*;
import sampleclass.A;
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

			for(FilterNode filter : filters)
			{
				System.out.println(filter.toString());
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}