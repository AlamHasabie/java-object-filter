package utils;

import org.w3c.dom.*;

public class TagHelper 
{
	public enum Tag 
	{
		FILTER("filter"),
		FIELD("field"),
		METHOD("method"),
		NAME("name"),
		VALUE("value"),

		// Sentinel unk from string->tag conversion
		UNK("unk");


		public String val;
	    private Tag(String label) {
        	this.val = label;
    	}
	}

	public static boolean isElement(Node node)
	{
		return node.getNodeType() == Node.ELEMENT_NODE;
	}

	public static boolean tagEquals(Node node, Tag tag)
	{
		return node.getNodeName().equals(tag.val);
	}

	public static Tag fromString(String s)
	{
		if(s.equals("filter"))
		{
			return Tag.FILTER;
		}

		if(s.equals("field"))
		{
			return Tag.FIELD;
		}

		if(s.equals("method"))
		{
			return Tag.METHOD;
		}

		if(s.equals("name"))
		{
			return Tag.NAME;
		}

		if(s.equals("value"))
		{
			return Tag.VALUE;
		}

		return Tag.UNK;
	}
}