public class MinerFull
	extends Miner
{	
	public MinerFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, 
			animationRate);			
		this.resourceCount = resourceLimit;			
	}
}