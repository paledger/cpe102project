import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.*;
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
	public void scheduleEntity(WorldModel world, List<String> iStore)
	{
	}
	
	public Entity tryTransformMinerFull(WorldModel world)
	{
		Entity newEntity = new MinerNotFull(this.name, this.resourceLimit, 
			this.position, this.rate, this.imgs, this.animationRate);
		return newEntity;
	}
	
	public ListBooleanPair minerToSmith(WorldModel world, Blacksmith smith)
	{
		Point entityPt = this.getPosition();
      LinkedList<Point> points = new LinkedList<Point>();
		
		if(!(smith instanceof Blacksmith))
		{
			points.add(entityPt);
			return new ListBooleanPair(points,false);
		}
		Point smithPt = smith.getPosition();
		if(entityPt.adjacent(smithPt))
		{
			smith.setResourceCount(
				smith.getResourceCount()+this.getResourceCount());
			this.setResourceCount(0);
			points.clear();
			return new ListBooleanPair(points,true);
		}
		else
		{
			Point newPt = smithPt.nextPosition(world, smithPt);
			//Point[] object = world.moveEntity(this,newPt);
			//See comments in worldmodel under moveEntity.
			List<Point> object = world.moveEntity(this,newPt);
			return new ListBooleanPair(object,false);
			
		}
	}
	
	public Object createMinerFullAction(WorldModel world, List<String> iStore)
	{
		Function<Integer, List<Point>> action = (currentTicks) ->
		{
			this.removePendingAction(action);
			
			Point entityPt = this.getPosition();
			Blacksmith b = new Blacksmith("null", null, 0, 0, null, 0);
			Entity smith = world.findNearest(entityPt, b);
			ListBooleanPair found = this.minerToSmith(world, (Blacksmith)smith);
			
			Miner newEntity = this;
			if (found.getBool())
			{
				//MinerNotFull newEntity = this.tryTransformMiner(WorldModel world, this.tryTransformMinerFull);
				
				world.scheduleAction(newEntity, newEntity.createMinerAction(world, iStore),
					currentTicks + newEntity.getRate());
			}
			return found.getEnt();
		};
		return action;
	}
	
	public Object createMinerAction(WorldModel world, List<String> imageStore)
	{
		return this.createMinerFullAction(world,imageStore);
	}
}