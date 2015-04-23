public abstract class PathyObject
{
	protected String id;
	protected String type;
	
	public static final String NODE = "Node";
	public static final String JUNCT = "Junct";
	public static final String ENTITY = "Entity";
	public static final String LINK = "Link";
	public static final String ACTION = "Action";

	public PathyObject(String _id, String _t)
	{
		id = _id;
		type = _t;
	}
		
	public String toString()
	{
		return "Type: " + type + " ID: " + id;
	}

	public String getID()
	{
		return id;
	}
	
	public String getType()
	{
		return type;
	}
}
