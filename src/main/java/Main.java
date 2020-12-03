import org.w3c.dom.*;
import conf.Loader;
import filter.FilterNode;
import java.lang.reflect.*;
import sampleclass.A;

public class Main
{
	public static void main(String[] args) 
	{
		try 
		{
			Document doc = Loader.load("sample.xml");
			Class target = A.class;
			Node root = doc.getDocumentElement();
			FilterNode.parse(doc, target);

		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
}