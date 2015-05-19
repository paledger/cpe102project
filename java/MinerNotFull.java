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
		
		if(ore==null)
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
			List<Point> object = world.moveEntity(this,newPt);
			ListBooleanPair output = new ListBooleanPair(object,false);
			return output;
			
		}
	}
	
	public void scheduleMiner(WorldModel world, int ticks, List<String> iStore)
	{
		world.scheduleAction(this, this.createMinerAction(world,iStore), ticks+this.rate);
		world.scheduleAnimation(this, 0);
	}
	
	public Object createMinerAction(WorldModel world, List<String> iStore)
	{
		Action[] funcs = { null }; 
		funcs[0] = (currentTicks) ->
		{
			this.removePendingAction(funcs[0]);
			
			Point entityPt = this.getPosition();
			Ore o = new Ore("null",null,null,0);
			Entity ore = world.findNearest(entityPt, o);
			ListBooleanPair found = this.minerToOre(world, (Ore)ore);
			
			Miner newEntity = this;
			if (found.getBool())
			{
				Function<WorldModel, Miner> transform = (worldt) ->
				{
					return (MinerFull)this.tryTransformMinerNotFull(worldt);
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