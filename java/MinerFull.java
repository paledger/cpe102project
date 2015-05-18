import java.util.ArrayList;
import processing.core.*;

public class MinerFull
	extends Miner
{	
	public MinerFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
					ArrayList<PImage> imgs,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, imgs, 
			animationRate);			
		this.resourceCount = resourceLimit;			
	}
	//missing schedule_entity
	
	//miner_to_smith: also uses world.move_entity, deals with tuple return of function
	//create_miner_full_action uses action methods
	
	public Entity tryTransformMinerFull(WorldModel world)
	{
		Entity newEntity = new MinerNotFull(this.name, this.resourceLimit, 
			this.position, this.rate, this.imgs, this.animationRate);
		return newEntity;
	}
}