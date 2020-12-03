import org.w3c.dom.*;
import conf.Loader;

public class Main
{
	public static void main(String[] args) 
	{
		try 
		{
			Document doc = Loader.load("sample.xml");
			Node root = doc.getDocumentElement();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
}