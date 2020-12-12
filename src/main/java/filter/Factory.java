package filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import conf.FilterConfigurationLoader;
import util.TagHelper;
import util.TagHelper.Tag;

import exceptions.ConfigurationLoadingException;
import exceptions.ParsingException;
import exceptions.SyntaxException;

public class Factory
{

	public static List<Group> generate()
		throws ParsingException, ConfigurationLoadingException
	{
		try {
			return parse(FilterConfigurationLoader.load("sample.xml"));

		} catch (IOException e)
		{
			throw new ConfigurationLoadingException(e);
		} catch (SAXException e)
		{
			throw new ConfigurationLoadingException(e);
		} catch (ParserConfigurationException e)
		{
			throw new ConfigurationLoadingException(e);
		}
	}

	private static List<Group> parse(Node root)
		throws ParsingException
	{
		List<Group> groups = new ArrayList();
		NodeList children = root.getChildNodes();

		for(int i = 0 ; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if(TagHelper.isElement(child))
			{
				if(!TagHelper.tagEquals(child, Tag.GROUP))
				{
					throw new SyntaxException("Expected <group> tag, found " + child.getNodeName());
				}

				groups.add(new Group(child));
			}
		}

		return groups;
	}
}