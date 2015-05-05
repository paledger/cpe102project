import java.util.ArrayList;
import java.util.Arrays;

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
	public Pair minerToOre(WorldModel world, Ore ore)
	{
		Point entityPt = this.getPosition();
		if (ore==null)
		{
			ArrayList<Point> entityPtL = new ArrayList<Point>(Arrays.asList(entityPt));
			ListBooleanPair pair = new ListBooleanPair(entityPtL,false);
			return pair;
		}
		if (entityPt.adjacent(ore.getPosition()))
		{
			this.setResourceCount(1+this.getResourceCount());
			//world.removeEntity(ore); -> removeentity has action functionality
			Point orePt = ore.getPosition();
			PointBooleanPair pair2 = new PointBooleanPair(orePt,true);
			return pair2;
			
		} /*else
		{
			Point newPt = entityPt.nextPosition(world, ore.getPosition);
			//from python:          return (world.move_entity(self, new_pt), False)
			//>>>> need to be able to return something with a function, commented out for now
		}*/
			return null;
		
	} 
	
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