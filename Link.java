public class Link extends PathyObject
{
	private PathyObject a;
	private PathyObject b;
	private int weight;
	
	public Link(String _id, Node _a, Node _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = 0;
	}
	
	public Link(String _id, Junction _a, Junction _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = 0;
	}
	
	public Link(String _id, Node _a, Junction _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = 0;
	}
	
	public Link(String _id, Junction _a, Node _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = 0;
	}
	
	public Link(String _id, PathyObject _a, PathyObject _b)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = 0;
	}
	
	
	public Link(String _id, Node _a, Node _b, int _w)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = _w;
	}
	
	public Link(String _id, Junction _a, Junction _b, int _w)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = _w;
	}
	
	public Link(String _id, Node _a, Junction _b, int _w)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = _w;
	}
	
	public Link(String _id, Junction _a, Node _b, int _w)
	{
		super(_id, PathyObject.LINK);
		a = _a;
		b = _b;
		weight = _w;
	}
	
	public Link(String _id, PathyObject _a, PathyObject _b, int _w)
	{
		super(_id, PathyObject.LINK);
		String throwline = "";
		if (!(_a instanceof Node || _a instanceof Junction))
		{
			throwline = "ERROR: A endpoint must be a Node or Junciton. PARAM 1";
		}
		if (!(_b instanceof Node || _b instanceof Junction))
		{
			throwline = (throwline.isEmpty() ? "" : throwline + System.lineSeparator());
			throwline = throwline + "ERROR: B endpoint must be a Node or Junciton. PARAM 2";
		}
		if (throwline.isEmpty())
		{
			throw new ClassCastException(throwline);
		}
		a = _a;
		b = _b;
		weight = _w;
	}
	
	public PathyObject getA()
	{
		return a;
	}
	
	public PathyObject getB()
	{
		return b;
	}
	
	public void setWeight(int w)
	{
		weight = w;
	}
	
	public int getWeight()
	{
		return weight;
	}
}
