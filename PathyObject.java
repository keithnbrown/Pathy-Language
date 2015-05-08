public abstract class PathyObject
{
	protected String id;
	protected PathyType type;
	
	//public static enum PathyType {NODE, JUNCT, ENTITY, LINK, ACTION};


	public PathyObject(String _id, PathyType link)
	{
		id = _id;
		type = link;
	}
		
	public String toString()
	{
		return "Type: " + type + " ID: " + id;
	}

	public String getID()
	{
		return id;
	}
	
	public PathyType getType()
	{
		return type;
	}
}
