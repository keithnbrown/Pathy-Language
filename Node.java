import java.util.HashSet;

public class Node extends PathyObject
{
	
	HashSet<Action> activities;
	
	public Node(String _id)
	{
		super(_id, PathyType.NODE);
		activities = new HashSet<Action>();
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
