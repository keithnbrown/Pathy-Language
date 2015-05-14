import java.util.HashSet;

public abstract class PathyPlace extends PathyObject
{
	protected HashSet<Link> connections;

	public PathyPlace(String _id, PathyType link)
	{
		super(_id, link);
		connections = new HashSet<Link>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		//I can't have it call connections.HashCode as it causes an infinite loop
		result = prime * result + ((connections == null) ? 0 : 5);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathyPlace other = (PathyPlace) obj;
		if (connections == null) {
			if (other.connections != null)
				return false;
		} else if (!connections.equals(other.connections))
			return false;
		return true;
	}

	public void checkConnections(Link a, Link b) {
		//if either link doesn't appear to be connected, throw
		
		boolean notConnA = !connections.contains(a);
		boolean notConnB = !connections.contains(b);
		if (notConnA && notConnB)
		{
			throw new RuntimeException("Error: \"" + this.id + "\" is not connected to Links called \"" + a.id + "\" or \"" + b.id + "\".");
		}
		else if (notConnA)
		{
			throw new RuntimeException("Error: \"" + this.id + "\" is not connected to a Link called \"" + a.id + "\".");
		}
		else if (notConnB)
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
	
	public boolean hasConnections()
	{
		return !connections.isEmpty();
	}
	
	public boolean isConnected(Link l)
	{
		return connections.contains(l);
	}
}
