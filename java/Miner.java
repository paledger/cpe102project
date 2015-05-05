public class Miner
    extends Animated
	 implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	public Miner(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         int animationRate)
	{
		super(name, position, rate, animationRate);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
	}
	public void setResourceCount(int n)
	{
		this.resource_count = n;
	}
	public int getResourceCount()
	{
		return this.resourceCount;
	}
	public int getResourceLimit()
	{
		return this.resourceLimit;
	}	
	//try_transform_miner, create_miner_action
	//missing imgs as well and currentImg
}