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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathyObject other = (PathyObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
