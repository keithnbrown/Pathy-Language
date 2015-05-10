public class Entity extends PathyObject
{
	private Node location;
	private int energy;

	public Entity(String _id, Node _s)
	{
		super(_id, PathyType.ENTITY);
		location = _s;
		energy = 0;
	}

	public Entity(String _id, Node _s, int _e)
	{
		super(_id, PathyType.ENTITY);
		location = _s;
		energy = _e;
	}

	public Node getLocation()
	{
		return location;
	}

	public void setLocation(Node _l)
	{
		location = _l;
	}

	public int getEnergy()
	{
		return energy;
	}

	public void setEnergy(int e)
	{
		energy = e;
	}

	public void modEnergy(int e)
	{
		energy += e;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + energy;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		Entity other = (Entity) obj;
		if (energy != other.energy)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
}
