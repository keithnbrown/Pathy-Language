import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Junction extends PathyObject
{
	public Junction(String _id)
	{
		super(_id, PathyType.JUNCT);
	}

	private HashSet<Link> connections;
	private HashMap<LinkPair, LinkDir> internalConnections; 

	private void checkConnections(Link a, Link b) {
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
		boolean ret = connections.remove(l);

		if (ret)
		{
			//check if it was involved in any internals
			Set<LinkPair> keys = internalConnections.keySet();
			for (LinkPair p : keys)
			{
				if (p.getA().equals(l) || p.getB().equals(l))
				{
					//that link was involved in a LinkPair, remove it.
					internalConnections.remove(p);
				}
			}
		}
		System.gc();
		return ret;
	}

	public void setInternal(Link a, Link b, LinkDir d)
	{
		checkConnections(a,b);
		LinkPair lp = new LinkPair(a,b);
		//it may seem odd removing then adding but this is done to make sure the new direction is correct as LinkPairs equate either way
		internalConnections.remove(lp);

		//if the direction is two way, don't add it again, as a route is implicitly two way if it's not in the map
		if (d != LinkDir.TWOWAY)
		{
			internalConnections.put(lp, d);
		}
	}

	public LinkDir getInternal(Link a, Link b)
	{
		checkConnections(a,b);
		LinkDir ret = LinkDir.TWOWAY;
		//this is not as simple as a return as we need to check the direction the internal is and flip it if a and b are the other way around.
		LinkPair lp = new LinkPair(a,b);
		LinkPair ilp = null;
		//check if it was involved in any internals
		Set<LinkPair> keys = internalConnections.keySet();
		for (LinkPair p : keys)
		{
			if (p.equals(lp))
			{
				ilp = p;
				break;
			}
		}
		if (ilp == null)
		{
			//if it didn't find an internal link pair then it's implicitly TWOWAY
			ret = LinkDir.TWOWAY;
		}
		else if (ilp.getA().equals(a) && ilp.getB().equals(b))
		{
			//if A=a and B=b then the links are in the same order so return without changing
			ret = internalConnections.get(ilp);
		}
		else if (ilp.getA().equals(b) && ilp.getB().equals(a))
		{
			//if A=b and B=a then the links are reversed. more checks needed.
			LinkDir tempret = internalConnections.get(ilp);
			if (tempret == LinkDir.ATOB)
			{
				ret = LinkDir.BTOA;
			}
			else if (tempret == LinkDir.ATOB)
			{
				ret = LinkDir.ATOB;
			}
			else
			{
				//if it's not a->b or b->a then by process of elimination it's blocked and can just be returned;
				ret = LinkDir.BLOCKED;
			}
		}
		else
		{
			//Oh dear, we shouldn't really get here. Throw.
			throw new RuntimeException("CRITICAL: Couldn't work out LinkPair orientation in relation to supplied Links");
		}

		return ret;
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
