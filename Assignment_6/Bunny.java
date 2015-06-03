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
			if(target.getClass() == Miner.class)
			{
         	toCarrot(world, target.getImages(), (Miner)target);
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
					System.out.print(old_entity.getClass());
					if(old_entity.getClass() == MinerFull.class ||
						old_entity.getClass() == MinerNotFull.class)
					{
						
            		toCarrot(world, old_entity.getImages(), (Miner)old_entity);
					} else
					{
						old_entity.remove(world);
					}
					
				}
         world.moveEntity(this, new_pt);
         return false;
      }
   }
	
	protected Carrot toCarrot(WorldModel world, List<PImage> imageStore, Miner miner)
	{
		Carrot carrot = new Carrot("carrot", getPosition(), 0, imageStore);
		//if(!(this.getClass()!=Carrot.class))
		//{
			miner.remove(world);
			world.addEntity(carrot);
			//carrot.scheduleAnimation(world);
			//}
		return carrot;
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