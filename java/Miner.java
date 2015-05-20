import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import processing.core.*;
import java.util.LinkedList;
import java.util.HashMap;

public abstract class Miner
    extends Animated
	 implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	public abstract Object createMinerAction(WorldModel world, HashMap<String, LinkedList<PImage>> iStore);
		
	public Miner(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
					LinkedList<PImage> imgs,
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
}