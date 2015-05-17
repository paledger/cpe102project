import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	
	public Entity tryTransformMiner(WorldModel world)
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
			Point object = world.moveEntity(this,newPt);
			points.add(object);
			return new ListBooleanPair(points,false);
			
		}
	}
	
	/*public Action createMinerFullAction(WorldModel world, List<String> iStore)
	{
		Action action = (currentTicks) ->
		{
			this.removePendingAction(action);
			
			Point entityPt = this.getPosition();
			Blacksmith smith = world.findNearest(entityPt, Blacksmith);
			ListBooleanPair tilesFound = this.minerToSmith(world, smith);
			
			Miner newEntity = this;
			if (tilesFound.getBool())
			{
				//MinerNotFull newEntity = this.tryTransformMiner(WorldModel world, this.tryTransformMinerFull);
				
				world.scheduleAction(newEntity, newEntity.createMinerAction(world, iStore),
					currentTicks + newEntity.getRate());
			}
			return tilesFound.getEnt();
		};
		return action;
	}
	*/	
}