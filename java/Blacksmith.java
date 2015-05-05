public class Blacksmith
	extends ResourceDistance
	implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	public Blacksmith(String name, Point position, 
		int resourceLimit, int rate, int resourceDistance)
	{
		super(name, position, rate, resourceDistance);
		this.resourceLimit = resourceLimit;
		this.resourceDistance = 1;
		this.resourceCount = 0;
		
	}
	public void setResourceCount(int n)
	{
		this.resourceCount = n;
	}
	public int getResourceCount()
	{
		return this.resourceCount;
	}
	public int getResourceLimit()
	{
		return this.resourceLimit;
	}
	//missing currentImg
}