public class Entity extends PathyObject
{
	private Node location;
	private int energy;
	
	public Entity(String _id, Node _s)
	{
		super(_id, PathyObject.ENTITY);
		location = _s;
		energy = 0;
	}
	
	public Entity(String _id, Node _s, int _e)
	{
		super(_id, PathyObject.ENTITY);
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
}
