public class MinerNotFull
	extends Miner
{	
	public MinerNotFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate,
			animationRate);	
		this.resourceCount = 0;	
		
		//missing currentImg and imgs
		//also missing schedule_entity, schedule_miner (has to do with actions)
		//also missing create_miner_not_full_action--uses action methods
	}
/*	public void miner_to_ore(WorldModel world, Ore ore)
	{
		private occupancy entityPt = this.getPosition();
		if (ore==null)
			//from python: "if not ore"
		{
			return ([entityPt], False);
		}
	} 
	// need to finish this--how to return things of this type?
	*/
	
	public Entity tryTransformMinerNotFull(WorldModel world)
	{
		if (this.resourceCount<this.resourceLimit)
		{
			return this;
		} else
		{
			MinerFull newEntity = new MinerFull(this.name, this.resourceLimit, 
				this.position, this.rate, this.animationRate);
			return newEntity;
		}
	}
	
}