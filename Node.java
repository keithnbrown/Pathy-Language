import java.util.HashSet;

public class Node extends PathyObject
{

	HashSet<Action> activities;

	public Node(String _id)
	{
		super(_id, PathyType.NODE);
		activities = new HashSet<Action>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((activities == null) ? 0 : activities.hashCode());
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
		Node other = (Node) obj;
		if (activities == null) {
			if (other.activities != null)
				return false;
		} else if (!activities.equals(other.activities))
			return false;
		return true;
	}

	public boolean addAction(Action a)
	{
		return activities.add(a);
	}

	public boolean removeAction(Action a)
	{
		return activities.remove(a);
	}

	public HashSet<Action> getActivities()
	{
		return activities;
	}
}
