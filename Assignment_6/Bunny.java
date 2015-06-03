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
	
   protected boolean move(WorldModel world)
   {
		Point newPt = this.getPosition();
		float randDirection = random(1,4);
		if(randDirection==1)
		{
			//up
			newPt = new Point(newPt.x(), newPt.y()+1);
		}
		if(randDirection==2)
		{
			//down
			newPt = new Point(newPt.x(), newPt.y()-1);
		}
		if(randDirection==3)
		{
			//left
			newPt = new Point(newPt.x()-1, newPt.y());
		}
		if(randDirection==4)
		{
			//right
			newPt = new Point(newPt.x()+1, newPt.y());
		}
		if(world.withinBounds(newPt))
		{
  		 world.moveEntity(this, nextPosition(world, newPt));
		}
		return true;
   }
	
   protected boolean canPassThrough(WorldModel world, Point pt)
   {
		return !world.isOccupied(pt);
   }
	
   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);

         //WorldEntity target = world.findNearest(getPosition(), seeking);

         Actor newEntity = this;
         if (move(world))
         {
            newEntity = this;
         }

         scheduleAction(world, newEntity,
            newEntity.createAction(world, imageStore),
            ticks + newEntity.getRate());
      };
      return action[0];
   }
	
}