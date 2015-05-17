import processing.core.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.function.*;

public class MinerNotFull
	extends Miner
{	
	public MinerNotFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
					ArrayList<PImage> imgs,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, imgs,
			animationRate);	
		this.resourceCount = 0;	
		//also missing schedule_entity, schedule_miner (has to do with actions)
	}
	
	public void scheduleEntity(WorldModel world, List<String> iStore)
	{
		this.scheduleMiner(world,0,iStore);
	}
	
	public Entity tryTransformMinerNotFull(WorldModel world)
	{
		if (this.resourceCount<this.resourceLimit)
		{
			return this;
		} else
		{
			MinerFull newEntity = new MinerFull(this.name, this.resourceLimit, 
				this.position, this.rate, this.imgs, this.animationRate);
			return newEntity;
		}
	}
	
	public ListBooleanPair minerToOre(WorldModel world, Ore ore)
	{
		Point entityPt = this.getPosition();
      LinkedList<Point> points = new LinkedList<Point>();
		
		if(!(ore instanceof Ore))
		{
			points.add(entityPt);
			return new ListBooleanPair(points,false);
		}
		Point orePt = ore.getPosition();
		if(entityPt.adjacent(orePt))
		{
			this.setResourceCount(1+this.getResourceCount());
			world.removeEntity(ore);
			points.add(orePt);
			return new ListBooleanPair(points,true);
		}
		else
		{
			Point newPt = entityPt.nextPosition(world, orePt);
			//Point[] object = world.moveEntity(this,newPt);
			//See comments in worldmodel under moveEntity.
			Point object = world.moveEntity(this,newPt);
			points.add(object);
			return new ListBooleanPair(points,false);
			
		}
	}
	
	public void scheduleMiner(WorldModel world, int ticks, List<String> iStore)
	{
		world.scheduleAction(this, this.createMinerAction(world,iStore), ticks+this.rate);
		world.scheduleAnimation(this, 0);
	}
	
	public Object createMinerNotFullAction(WorldModel world, List<String> iStore)
	{
		Function<Integer, List<Point>> action = (currentTicks) ->
		{
			this.removePendingAction(action);
			
			Point entityPt = this.getPosition();
			Ore ore = world.findNearest(entityPt, Ore);
			ListBooleanPair found = this.minerToOre(world, ore);
			
			Miner newEntity = this;
			if (found.getBool())
			{
				//MinerFull newEntity = this.tryTransformMiner(world, this::tryTransformMinerNotFull);
				//MinerFull newEntity = this.tryTransformMiner(WorldModel world, 
				//	this.tryTransformMinerNotFull());
				//urk i don't know how to do this
				
				world.scheduleAction(newEntity, newEntity.createMinerAction(world, iStore),
					currentTicks + newEntity.getRate());
			}
			return found.getEnt();
		};
		return action;
	}
	
	public Object createMinerAction(WorldModel world, List<String> imageStore)
	{
		return this.createMinerNotFullAction(world,imageStore);
	}
	
}