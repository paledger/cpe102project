import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import processing.core.*;

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
					ArrayList<PImage> imgs,
		         int animationRate)
	{
		super(name, position, rate, imgs, animationRate);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
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
/*	
	Things need to be sorted out with WorldModel before this will work. Is "world" a
	worldmodel object? How to get transform to work?
		*/
	public Entity tryTransformMiner(WorldModel world, Function<WorldModel, Miner> transform)
	{
		Entity newEntity = transform.apply(world);
		if(this!= newEntity)
		{
			world.clearPendingActions();
			world.removeEntityAt(this.position);
			world.addEntity(newEntity);
			world.scheduleAnimation(newEntity);
		}
		return newEntity;
	}
	
	public Action createMinerAction(WorldModel world, List<String> iStore)
	{
		if(this instanceof MinerNotFull)
		{
			return this.createMinerNotFullAction(world,iStore);
		}
		else
		{
			return this.createMinerFullAction(world,iStore);
		}
	}
	//create_miner_action
//*/
}