public class Action extends PathyObject
{
	private int refs;
	public Action(String _id)
	{
		super(_id, PathyType.ACTION);
		refs = 0;
	}
	
	public void inc()
	{
		refs++;
	}
	
	public void dec()
	{
		refs--;
	}
}
