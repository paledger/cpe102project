import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.*;
import processing.core.*;
import java.util.HashMap;

public class MinerFull
	extends Miner
{	
	public MinerFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
					LinkedList<PImage> imgs,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, imgs, 
			animationRate);			
		this.resourceCount = resourceLimit;			
	}

	public void scheduleEntity(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
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
		
		if(smith==null)
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
			List<Point> object = world.moveEntity(this,newPt);
			ListBooleanPair output = new ListBooleanPair(object,false);
			return output;
			
		}
	}
	
	public Object createMinerAction(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
	{
		Action[] funcs = { null };
		funcs[0] = (currentTicks) ->
		{
			this.removePendingAction(funcs[0]);
			
			Point entityPt = this.getPosition();
			Blacksmith b = new Blacksmith("null",null,0,0,null,0);
			Entity smith = world.findNearest(entityPt, b);
			ListBooleanPair found = this.minerToSmith(world, (Blacksmith)smith);
			
			Miner newEntity = this;
			if (found.getBool())
			{
				Function<WorldModel, Miner> transform = (worldt) ->
				{
					return (MinerNotFull)this.tryTransformMinerFull(worldt);
				};
				
				newEntity = 
					(Miner)this.tryTransformMiner(world, transform);
			}
			world.scheduleAction(newEntity, newEntity.createMinerAction(world, iStore),
				currentTicks + newEntity.getRate());
			
			return found.getEnt();
		};
		return funcs[0];
	}
}