import java.util.HashSet;

public abstract class PathyPlace extends PathyObject
{

	public PathyPlace(String _id, PathyType link)
	{
		super(_id, link);
	}
	protected HashSet<Link> connections;

	protected void checkConnections(Link a, Link b) {
		//if either link doesn't appear to be connected, throw
		if (!connections.contains(a) && !connections.contains(b))
		{
			throw new RuntimeException("Error: \"" + this.id + "\" is not connected to Links called \"" + a.id + "\" or \"" + b.id + "\".");
		}
		else if (!connections.contains(a))
		{
			throw new RuntimeException("Error: \"" + this.id + "\" is not connected to a Link called \"" + a.id + "\".");
		}
		else if (!connections.contains(b))
		{
			throw new RuntimeException("Error: \"" + this.id + "\" is not connected to a Link called \"" + b.id + "\".");
		}
	}

	public HashSet<Link> getConnections()
	{
		return connections;
	}

	public boolean addConnection(Link l)
	{
		return connections.add(l);
	}

	public boolean removeConnection(Link l)
	{
		return connections.remove(l);
	}
	
	public boolean isConnected()
	{
		return !connections.isEmpty();
	}
}
