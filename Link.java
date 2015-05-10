public class Link extends PathyObject
{
	private PathyObject a;
	private PathyObject b;
	private int weight;
	private LinkDir direction;

	private void checkNotNull(PathyObject _a, PathyObject _b)
	{
		if (_a == null && _b == null)
		{
			throw new RuntimeException("Endpoints A and B must be non-null");
		}
		if (_a == null)
		{
			throw new RuntimeException("Endpoint A must be non-null");
		}
		if (_b == null)
		{
			throw new RuntimeException("Endpoint B must be non-null");
		}
		if (_a == _b || _a.equals(_b))
		{
			throw new RuntimeException("Endpoints must not be identical");
		}
	}

	public Link(String _id, Node _a, Node _b)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Junction _a, Junction _b)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_a.addConnection(this);
		_b.addConnection(this);
		weight = 0;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Node _a, Junction _b)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_b.addConnection(this);
		weight = 0;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Junction _a, Node _b)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_a.addConnection(this);
		weight = 0;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, PathyObject _a, PathyObject _b)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
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

		if (_a.getType() == PathyType.JUNCT)
		{
			Junction aJunct = (Junction) _a;
			aJunct.addConnection(this);
		}

		if(_b.getType() == PathyType.JUNCT)
		{
			Junction bJunct = (Junction) _b;
			bJunct.addConnection(this);
		}

		a = _a;
		b = _b;
		weight = 0;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Node _a, Node _b, int _w)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Junction _a, Junction _b, int _w)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_a.addConnection(this);
		_b.addConnection(this);
		weight = _w;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Node _a, Junction _b, int _w)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_b.addConnection(this);
		weight = _w;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, Junction _a, Node _b, int _w)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_a.addConnection(this);
		weight = _w;
		direction = LinkDir.TWOWAY;
	}

	public Link(String _id, PathyObject _a, PathyObject _b, int _w)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
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

		if (_a.getType() == PathyType.JUNCT)
		{
			Junction aJunct = (Junction) _a;
			aJunct.addConnection(this);
		}

		if(_b.getType() == PathyType.JUNCT)
		{
			Junction bJunct = (Junction) _b;
			bJunct.addConnection(this);
		}

		a = _a;
		b = _b;
		weight = _w;
		direction = LinkDir.TWOWAY;
	}


	public Link(String _id, Node _a, Node _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		weight = _w;
		direction = _d;
	}

	public Link(String _id, Junction _a, Junction _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_a.addConnection(this);
		_b.addConnection(this);
		weight = _w;
		direction = _d;
	}

	public Link(String _id, Node _a, Junction _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_b.addConnection(this);
		weight = _w;
		direction = _d;
	}

	public Link(String _id, Junction _a, Node _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
		a = _a;
		b = _b;
		_a.addConnection(this);
		weight = _w;
		direction = _d;
	}

	public Link(String _id, PathyObject _a, PathyObject _b, int _w, LinkDir _d)
	{
		super(_id, PathyType.LINK);
		checkNotNull(_a,_b);
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

		if (_a.getType() == PathyType.JUNCT)
		{
			Junction aJunct = (Junction) _a;
			aJunct.addConnection(this);
		}

		if(_b.getType() == PathyType.JUNCT)
		{
			Junction bJunct = (Junction) _b;
			bJunct.addConnection(this);
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

	public boolean isEndpoint(PathyObject end)
	{
		return end == a || end == b;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + weight;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//most of equals is a bit redundant as if (this == obj) returns false then super.equals(obj) will likely do so too as ids won't be the same
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		//altered auto-gen code as equals should auto-fail if the endpoint of either object is null
		if (other.a == null || a == null) {
			return false;
		} else if (!a.equals(other.a))
			return false;
		if (other.b == null || b == null) {
			return false;
		} else if (!b.equals(other.b))
			return false;
		if (direction != other.direction)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	//identical is the same as equals but doesn't check the id, so this is used to compare if two different links do exactly the same things.
	public boolean identical(Object obj)
	{
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (type != other.type)
			return false;
		if (other.a == null || a == null) {
			return false;
		} else if (!a.equals(other.a))
			return false;
		if (other.b == null || b == null) {
			return false;
		} else if (!b.equals(other.b))
			return false;
		if (direction != other.direction)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
}
