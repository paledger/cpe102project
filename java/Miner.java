import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import processing.core.*;

public abstract class Miner
    extends Animated
	 implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	abstract Object createMinerAction(WorldModel world, List<String> imageStore);
		
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
		Animated newEntity = transform.apply(world);
		if(this!= newEntity)
		{
			world.clearPendingActions(this);
			world.removeEntityAt(this.position);
			world.addEntity(newEntity);
			world.scheduleAnimation(newEntity, 0);
		}
		return newEntity;
	}
	
/*	public Action createMinerAction(WorldModel world, List<String> iStore)
	{
		if(this instanceof MinerNotFull)
		{
			return this.createMinerNotFullAction(world,iStore);
		}
		else
		{
			return this.createMinerFullAction(world,iStore);
		}
	}*/
}