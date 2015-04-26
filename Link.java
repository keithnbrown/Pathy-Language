public class Link extends PathyObject
{
	private PathyObject a;
	private PathyObject b;
	
	public Link(String _id, Node _a, Node _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
	}
	
	public Link(String _id, Junction _a, Junction _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
	}
	
	public Link(String _id, Node _a, Junction _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
	}
	
	public Link(String _id, Junction _a, Node _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
	}
	
	public String getAType()
	{
		return a.getType();
	}
	
	public String getBType()
	{
		return b.getType();
	}
	
	public String getAName()
	{
		return a.getID();
	}
	
	public String getBName()
	{
		return b.getID();
	}
}
