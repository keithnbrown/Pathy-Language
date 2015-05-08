public class Link extends PathyObject
{
	private PathyObject a;
	private PathyObject b;
	private int weight;
	private LinkDir direction;
	
	public Link(String _id, Node _a, Node _b)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, Junction _a, Junction _b)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, Node _a, Junction _b)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, Junction _a, Node _b)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, PathyObject _a, PathyObject _b)
	{
		super(_id, PathyType.LINK);
		boolean throwing = false;
		String throwline = "";
		if (!(_a.getType() == PathyType.NODE || _a.getType() == PathyType.JUNCT))
		{
			throwline = "ERROR: A endpoint must be a Node or Junciton. PARAM 1";
			throwing = true;
		}
		if (!(_b.getType() == PathyType.NODE || _b.getType() == PathyType.JUNCT))
		{
			throwline = (throwline.isEmpty() ? "" : throwline + System.lineSeparator());
			throwline = throwline + "ERROR: B endpoint must be a Node or Junciton. PARAM 2";
			throwing = true;
		}
		if (throwing)
		{
			throw new ClassCastException(throwline);
		}
		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}	
	
	public Link(String _id, Node _a, Node _b, int _w)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, Junction _a, Junction _b, int _w)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, Node _a, Junction _b, int _w)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, Junction _a, Node _b, int _w)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}
	
	public Link(String _id, PathyObject _a, PathyObject _b, int _w)
	{
		super(_id, PathyType.LINK);
		boolean throwing = false;
		String throwline = "";
		if (!(_a.getType() == PathyType.NODE || _a.getType() == PathyType.JUNCT))
		{
			throwline = "ERROR: A endpoint must be a Node or Junciton. PARAM 1";
			throwing = true;
		}
		if (!(_b.getType() == PathyType.NODE || _b.getType() == PathyType.JUNCT))
		{
			throwline = (throwline.isEmpty() ? "" : throwline + System.lineSeparator());
			throwline = throwline + "ERROR: B endpoint must be a Node or Junciton. PARAM 2";
			throwing = true;
		}
		if (throwing)
		{
			throw new ClassCastException(throwline);
		}
		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}


	public Link(String _id, Node _a, Node _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = _d;
	}
	
	public Link(String _id, Junction _a, Junction _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = _d;
	}
	
	public Link(String _id, Node _a, Junction _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = _d;
	}
	
	public Link(String _id, Junction _a, Node _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		a = _a;
		b = _b;
		weight = _w;
		direction = _d;
	}
	
	public Link(String _id, PathyObject _a, PathyObject _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		boolean throwing = false;
		String throwline = "";
		if (!(_a.getType() == PathyType.NODE || _a.getType() == PathyType.JUNCT))
		{
			throwline = "ERROR: A endpoint must be a Node or Junciton. PARAM 1";
			throwing = true;
		}
		if (!(_b.getType() == PathyType.NODE || _b.getType() == PathyType.JUNCT))
		{
			throwline = (throwline.isEmpty() ? "" : throwline + System.lineSeparator());
			throwline = throwline + "ERROR: B endpoint must be a Node or Junciton. PARAM 2";
			throwing = true;
		}
		if (throwing)
		{
			throw new ClassCastException(throwline);
		}
		a = _a;
		b = _b;
		weight = _w;
		direction = _d;
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
	
	public void setDirection(LinkDir d)
	{
		direction = d;
	}
	
	public LinkDir getDirection()
	{
		return direction;
	}
}
