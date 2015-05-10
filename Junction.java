import java.util.HashSet;

public class Junction extends PathyObject
{
	public Junction(String _id)
	{
		super(_id, PathyType.JUNCT);
	}

	private HashSet<Link> connections;
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((connections == null) ? 0 : connections.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Junction other = (Junction) obj;
		if (connections == null) {
			if (other.connections != null)
				return false;
		} else if (!connections.equals(other.connections))
			return false;
		return true;
	}
}
