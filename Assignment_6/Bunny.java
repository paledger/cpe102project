import processing.core.*;
import java.util.List;

public class Bunny
   extends MobileAnimatedActor
{
   public Bunny(String name, Point position, int rate, int animation_rate,
      List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }
	
	private Point randomPoint(WorldModel world)
	{
		float randx = random(0, world.getNumCols());
		float randy = random(0, world.getNumRows());
		Point newpt = new Point((int)randx, (int)randy);
		return newpt;
	}
		
   private boolean move(WorldModel world, WorldEntity target)
   {
		//Bunnies will remove all entities--including the water. Might need to be fixed.
		//Exception: It tries to transform the miners to carrots, something is going wrong
		//during that process.
      if (target == null)
      {
         return false;
      }
      if (adjacent(getPosition(), target.getPosition()))
      {
			//if the target is any of these classes
			if(target.getClass() == MinerFull.class ||
				target.getClass() == MinerNotFull.class || 
					target.getClass() == OreBlob.class)
			{
         	toCarrot(world, Main.imageStore, target);
			}else 
			{
				target.remove(world);
			}
         return true;
      }
      else
      {
         Point new_pt = nextPosition(world, randomPoint(world));
         WorldEntity old_entity = world.getTileOccupant(new_pt);
         if (old_entity != null && old_entity != this)
         	{
					//if the target is any of these classes
					if(old_entity.getClass() == MinerFull.class ||
						old_entity.getClass() == MinerNotFull.class ||
							old_entity.getClass() == OreBlob.class)
					{
						
            		toCarrot(world, Main.imageStore, old_entity);
					} else
					{
						old_entity.remove(world);
					}
					
				}
				if(world.withinBounds(new_pt) && canPassThrough(world, new_pt))
				{
        	 		world.moveEntity(this, new_pt);
				}
        	return false;
      }
   }
	
	protected Carrot toCarrot(WorldModel world, ImageStore imageStore, WorldEntity entity)
	{
		Carrot carrot = new Carrot("carrot", entity.getPosition(), 0, imageStore.get("carrot"));
		entity.remove(world);
		world.addEntity(carrot);
		return carrot;
	}
	
   protected boolean canPassThrough(WorldModel world, Point pt)
   {
		if(world.isOccupied(pt))
		{
			WorldEntity occupant = world.getTileOccupant(pt);
			if(!(occupant.getClass()==Obstacle.class) && !(occupant.getClass()==Blacksmith.class)
				&& !(occupant.getClass()==Bunny.class))
			{
				return true;
			}
		}
		else
		{
			return !(world.isOccupied(pt));
		}
		return false;
   }
	
   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);

         //WorldEntity target = world.findNearest(getPosition(), Miner.class);
			WorldEntity target = world.findNearest(getPosition(), Miner.class);
         long nextTime = ticks + getRate();

         if (target != null)
         {
           // Point tPt = target.getPosition();
				//Point tPt = randomPoint(world);
				
            if (move(world, target))
            {
               nextTime = nextTime + getRate();
            }
         }

         scheduleAction(world, this, createAction(world, imageStore),
            nextTime);
         
      };
      return action[0];
   }
		
	
}