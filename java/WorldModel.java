import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math.*;
import java.util.Comparator;
import java.util.function.*;
import processing.core.*;


public class WorldModel
{
   private int num_rows;
   private int num_cols;
   private Background backgroundd;
	private Grid background;
   private Grid occupancy;
   private static LinkedList<Entity> entities;
   private Entity none;
	private ArrayList<ActionTimePair> actionQueue;

   public WorldModel(int num_rows, int num_cols, Background backgroundd)
   {
      this.num_rows = num_rows;
      this.num_cols = num_cols;
      this.backgroundd = backgroundd;
		this.background = new Grid(num_cols, num_rows, backgroundd);
      Grid occupancy = new Grid(num_cols, num_rows, null);
      LinkedList<Entity> entities = new LinkedList<Entity>();
		this.entities = entities;
      this.actionQueue = new ArrayList<ActionTimePair>();
		//hopefully that's correct.
   }

   public Grid background()
   {
      return this.background;
   }

   public int num_cols()
   {
      return this.num_cols;
   }

   public int num_rows()
   {
      return this.num_rows;
   }

   public Grid occupancy()
   {
      return this.occupancy;
   }

   public List<Entity> entities()
   {
      return this.entities;
   }

   Function<List<DistPair>, Entity> nearestEntity = (entityDists) ->  
   {
      Entity nearest = null;
      if (entityDists.size() > 0)
      {
         DistPair pair = entityDists.get(0);
         for (int i = 1; i < entityDists.size(); i ++)
         {
            DistPair other = entityDists.get(i);
            if (other.getDist() < pair.getDist())
            {
               pair = other;
            }
         }
         nearest = pair.getEnt();
      } 
      return nearest;
   };

   public Entity findNearest(Point pt, Entity t)
   {
      ArrayList<DistPair> oftype = new ArrayList<DistPair>();
      for(int i = 0; i < this.entities.size(); i ++)
      {
			Class<?> eClass = t.getClass();
         if ((entities.get(i).getClass() == eClass))
         {
            Entity e = this.entities.get(i);
            DistPair pair = new DistPair(e, pt.distanceSq(e.getPosition()));
            oftype.add(pair);
         }
      }
      return nearestEntity.apply(oftype);
   }

  public List<Point> moveEntity(Entity entity, Point pt)
   {
		List<Point> tiles = new LinkedList<Point>();
		if (pt.withinBounds(this))
		{
			Point oldPt = entity.getPosition();
			this.occupancy.setCell(oldPt, null);
			tiles.add(oldPt);
			this.occupancy.setCell(pt, entity);
			tiles.add(pt);
			entity.setPosition(pt);
		}
		return tiles;
   } 

   public PImage getBackgroundImage(Point pt)
   {
      if(pt.withinBounds(this))
      {
         Actionable actionable = (Actionable)this.background.getCell(pt);
         return actionable.getImage();
			//The method getImage() requires it to be an Actionable item because
			//currentImg is only in Actionable. The issue is that
			//this runs regardless, and therefore returns "cannot find symbol".
      }
      return null;
   }

   public Object getBackground(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return this.background.getCell(pt);
      } 
      return null;
   }

   public void setBackground(Point pt, Background bgnd)
   {
      if(pt.withinBounds(this))
      {
         this.background.setCell(pt, bgnd);
      }
   }

   public Entity getTileOccupant(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return (Entity)this.occupancy().getCell(pt);
      }
      return null;
   }

   public void removeEntityAt(Point pt)
   {
      if(pt.withinBounds(this) && 
         this.occupancy().getCell(pt) != none)
      {
         Entity entity = (Entity)this.occupancy().getCell(pt);
         Point newPos = new Point(-1, -1);
         entity.setPosition(newPos);
         this.entities.remove(entity);
         this.occupancy().setCell(pt, none);
      }
   }


   /*
   public void handle_timer_event(WorldView view)
   {
      rects = update_on_time(pygame.time.get_ticks());
      view.update_view_tiles(rects);
   }
   */

   public void addEntity(Entity entity)
   {
      Point pt = entity.getPosition();
      if(pt.withinBounds(this))
      {
         Entity oldEntity = (Entity)this.occupancy().getCell(pt);
         if(oldEntity != null)
         {
           	 this.clearPendingActions((Actionable)oldEntity);					
         }
         this.occupancy().setCell(pt, entity);
         this.entities.addLast(entity);
      }
   }

   public void removeEntity(Actionable entity)
   {
      for(Object action:entity.getPendingActions())
      {
         this.unscheduleAction(action);
      }
      entity.clearPendingActions();
      this.removeEntityAt(entity.getPosition());
   }

   public void scheduleAction(Actionable entity, Object action, int time)
   {
      entity.addPendingAction(action);
		ActionTimePair input = new ActionTimePair(action, time);
      this.actionQueue.add(input);
   }

   public void unscheduleAction(Object action)
   {
      this.actionQueue.remove(action);
   }
	
   public void clearPendingActions(Actionable entity)
   {
      for(Object action:entity.getPendingActions())
      {
         this.unscheduleAction(action);
      }
      entity.clearPendingActions();
   }

   public Object createEntityDeathAction(Actionable entity)
   {
      Action[] func = {null};
      func[0] = (currentTicks) ->
      {
         entity.removePendingAction(func[0]);
         Point pt = entity.getPosition();
         this.removeEntity(entity);

			LinkedList<Point> output  = new LinkedList<Point>();
			output.add(pt);
         return output;
      };
      return func[0];
   }

   public void scheduleAnimation(Animated entity, int repeatCount)
   {
		repeatCount = 0;
      Object action = this.createAnimationAction(entity, repeatCount);
      int animationRate = entity.getAnimationRate();
      this.scheduleAction(entity, action, animationRate);
   }

   public Object createAnimationAction(Animated entity, int repeatCount)
   {
		Action[] func = { null };
      func[0] = (currentTicks) ->
		{
			entity.removePendingAction(func[0]);
			entity.nextImage();
			if (repeatCount !=1)
			{
				this.scheduleAction(entity,
					this.createAnimationAction(entity, Math.max(repeatCount-1, 0)),
						currentTicks+entity.getAnimationRate());
			}
			LinkedList<Point> output = new LinkedList<Point>();
			output.add(entity.getPosition());
			return output;
		};
      return func[0];
   }
	/*
   public List<Action> update_on_time(int ticks)
   {
      List<Action> tiles = new LinkedList<Action>();

      Action next = this.actionQueue.head();
      while(next && next.ord < ticks)
      {
         this.actionQueue.pop();
         tiles.addAll(next.item(ticks));
         Action next = this.actionQueue.head();
      }

      return tiles;
   }
		*/

}
