package conf;

import org.xml.sax.SAXException;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FilterConfigurationLoader
{
	public static Document load(String filename)
		throws IOException, SAXException, ParserConfigurationException
	{
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder();

		return builder.parse(new File(filename));
	}
}