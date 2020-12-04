package filter;

import utils.ITreeToString;

public abstract class Filter implements ITreeToString
{
	protected FilterNode filterNode;
	protected Class c;
	protected boolean isLeaf;
	protected String value;

	public abstract boolean shouldFilter(Object o, Class c);
}