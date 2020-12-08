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
		CLASS("class"),
		GROUP("group"),
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
		for(Tag t : Tag.values())
		{
			if(s.equals(t.val))
			{
				return t;
			}
		}
		return Tag.UNK;
	}
}