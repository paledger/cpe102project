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
      if (target == null)
      {
         return false;
      }

      if (adjacent(getPosition(), target.getPosition()))
      {
			///// THIS IS THE THING THAT IS REMOVING THE MINERS
			if(target instanceof Miner)
			{
         	Miner.toCarrot(world, ********target.getImages(), target);
			} else
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
            old_entity.remove(world);
         }
         world.moveEntity(this, new_pt);
         return false;
      }
   }
	
   protected boolean canPassThrough(WorldModel world, Point pt)
   {
		if(world.isOccupied(pt))
		{
			WorldEntity occupant = world.getTileOccupant(pt);
			if(!(occupant.getClass()==Miner.class))
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
			WorldEntity target = world.findNearest(getPosition(), Bunny.class);
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