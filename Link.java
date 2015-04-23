public class Link extends PathyObject
{
	private PathyObject a;
	private PathyObject b;
		
	
	public Link(String _id, Node _a, Node _b)
	{
		super(_id, PathyObject.LINK);
		
	}
	public Link(String _id, Junction _a, Junction _b)
	{
		super(_id, PathyObject.LINK);
		
	}
	public Link(String _id, Node _a, Junction _b)
	{
		super(_id, PathyObject.LINK);
		
	}
	public Link(String _id, Junction _a, Node _b)
	{
		super(_id, PathyObject.LINK);
		
	}
}
