import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class WorldModel
{
   private int num_rows;
   private int num_cols;
   private Grid background;
   private Grid occupancy;
   private List<Entity> entities;
   private Entity none;

   public WorldModel(int num_rows, int num_cols, Grid background)
   {
      this.num_rows = num_rows;
      this.num_cols = num_cols;
      this.background = background;
      Grid occupancy = new Grid(num_cols, num_rows, none);
      List<Entity> entities = new LinkedList<Entity>();
 //     List<String> actionQueue = new LinkedList<String>();
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

   // LAMBDA EXPRESSION FOR "NEAREST_ENTITY" FUNTION
   Entity nearestEntity = (ArrayList<DistPair> entityDists) -> 
   {
      Entity nearest = none;
      if (entityDists.length > 0)
      {
         DistPair pair = entity_dists[0];
         for (int i = 1; i < entityDists.length; i ++)
         {
            DistPair other = entityDists(i);
            if (other.getDist() < pair.getDist())
            {
               pair = other;
            }
         }
         nearest = pair.getEnt();
      } 
      return nearest;
   };
   // END OF LAMBDA EXPRESSION FOR "NEAREST_ENTITY" FUNCTION

   public Entity find_nearest(Point pt, Class type)
   {

   }

   public List moveEntity(Entity entity, Point pt)
   {
      List<Point> tiles = new LinkedList<Point>();
      if(pt.withinBounds(this))
      {
         Point oldPt = entity.getPosition();
         this.occupancy().setCell(oldPt, none);
         tiles.add(oldPt);
         this.occupancy().setCell(pt, entity);
         tiles.add(pt);
         entity.setPosition(pt);
      }
   }

   /*
   public Image getBackgroundImage(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return this.background.getCell(pt).getImage();
      }
   }
   */

   public Entity getBackground(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return this.background.getCell(pt);
      }
   }

   public void setBackground(Point pt, Entity bgnd)
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
         return this.occupancy().getCell(pt);
      }
   }

   public List<Entity> getEntities()
   {
      return this.entities;
   }

   public void removeEntityAt(Point pt)
   {
      if(pt.withinBounds(this) && 
         this.occupancy().getCell(pt) != none)
      {
         Entity entity = this.occupancy().getCell(pt);
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


/*
   public void addEntity(Entity entity)
   {
      pt = entity.getPosition();
      if(pt.withinBounds())
      {
         oldEntity = this.occupancy().getCell(pt);
         if(oldEntity != null)
         {
            oldEntity.clearPendingActions();
         }
         this.occupancy().setCell(pt, entity);
         this.entities.addLast(entity);
      }
   }

   public void removeEntity(Entity entity)
   {
      List<Type> list = entity.getPendingActions();
      for(int action = 0; action < list.length; action ++)
      {
         this.unscheduleAction(list[action]);
      }
      entity.clearPendingActions();
      this.removeEntityAt(entity.getPosition());
   }
*/


/*
   public void scheduleAction(Entity entity, Type action, int time)
   {
      entity.addPendingAction(action);
      this.actionQueue.add(time, action);
   }

   public void unscheduleAction(Type action)
   {
      this.actionQueue.remove(action);
   }

   public void clearPendingActions(Entity entity)
   {
      for(action : actionQueue)
      {
         this.unscheduleAction(action);
      }
      entity.clearPendingActions();
   }

   public  createEntityDeathAction(Entity entity)
   {
      public List<Point> action(int currentTicks)
      {
         entity.removePendingActions(action);
         Point pt = entity.getPosition();
         this.removeEntity(entity);
         return Linkedist<Point>(pt);  // QUESTIONABLE!!!!
      }
      return action;   // MIGHT MAKE THIS A LAMBDA EXPRESSION
   }

   public void scheduleAnimation(Entity entity, int repeatCount = 0)
   {
      Type action = this.createAnimationAction(entity, repeatCount);
      int animationRate = entity.getAnimationRate();
      this.scheduleAction(entity, action, animationRate);
   }

   public Type createAnimationAction(Entity entity, int repeatCount)
   {
      public action(int currentTicks)
      {
         entity.removePendingAction(action);
         entity.nextImage();
         if (repeatCount != 1)
         {
            this.scheduleAction(entity,
               this.createAnimationAction(entity, max(repeatCount - 1, 0)),
               currentTicks + entity.getAnimationRate());
         }
         return LinkedList<Point>(entity.getPosition()); // MIGHT MAKE THIS A LAMBDA EXPRESSION
      }
      return action;
   }

   public List<Point> update_on_time(int ticks)
   {
      List<Point> tiles = new LinkedList<Point>();

      Type next = this.actionQueue.head(); // CONFUSED ABOUT WHAT HEAD() DOES
      while(next && next.ord < ticks)
      {
         this.actionQueue.pop();
         tiles.addAll(next.item(ticks)); //confused aboout what next is ??? and item??
         Type next = this.actionQueue.head();
      }

      return tiles;
   }
   */

}
