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
}