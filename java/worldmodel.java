public class WorldModel
{
   private Background background;
   private int num_rows;
   private int num_cols;

   public WorldModel(int num_rows, int num_cols, Background background)
   {
      this.background = background;
      this.num_rows = num_rows;
      this.num_cols = num_cols;
      List<Entity> occupancy = new occ_grid.Grid(num_cols, num_rows, None);
      Linkedist<Entity> entities = new LinkedList<Entity>();
      LinkedList<String> actionQueue = new LinkedList<String>();
   }

   public void handle_timer_event(WorldView view)
   {
      rects = update_on_time(pygame.time.get_ticks());
      view.update_view_tiles(rects);
   }

   // LAMBDA EXPRESSION FOR "NEAREST_ENTITY" FUNTION
   Entity nearestEntity = (List entityDists) -> 
   {
      if (entity_dists.length > 0)
      {
         pair = entity_dists[0];
         for (int other = 1; other < entity_dists.length; other ++)
         {
            if (other[1] < pair[1])
            {
               pair = other;
            }
         }
         nearest = pair[0];
      } else
      {
         nearest = None;
      }
      return nearest;
   };
   // END OF LAMBDA EXPRESSION FOR "NEAREST_ENTITY" FUNCTION

   public Entity find_nearest(Point pt, Class type)
   {
        
   }

   public List moveEntity(Entity entity, Point pt)
   {
      List tiles = new LinkedList<Point>();
      if(pt.withinBounds())
      {

      }
   }

   public void addEntity(Entity entity)
   {
      pt = entity.getPosition();
      if(pt.withinBounds())
      {
         oldEntity = this.occupancy.getCell(pt);
         if(oldEntity != None)
         {
            oldEntity.clearPendingActions();
         }
         this.occupancy.setCell(pt, entity);
         this.entities.addLast(entity);
      }
   }

   public void removeEntity(Entity entity)
   {
      for(action : entity.getPendingActions())
      {
         this.unscheduleAction(action);
      }
      entity.clearPendingActions();
      this.removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pt)
   {
      if(pt.withinBounds() && 
         this.occupancy.getCell(pt) != None)
      {
         entity = this.occupancy.getCell(pt);
         Point newPos = new Point(-1, -1);
         entity.setPosition(newPos);
         this.entities.remove(entity);
         this.occupancy.setCell(pt, None);
      }
   }

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
      List tiles = new LinkedList<Point>();

      Type next = this.actionQueue.head(); // CONFUSED ABOUT WHAT HEAD() DOES
      while(next && next.ord < ticks)
      {
         this.actionQueue.pop();
         tiles.addAll(next.item(ticks)); //confused aboout what next is ??? and item??
         Type next = this.actionQueue.head();
      }

      return tiles;
   }

   public Image getBackgroundImage(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return this.background.getCell(pt).getImage();
      }
   }

   public Point getBackground(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return this.background.getCell(pt);
      }
   }

   public void setBackground(Point pt, Background bgnd)
   {
      if(pt.withinBounds(this))
      {
         this.background.setCell(pt, bgnd);
      }
   }

   public boolean getTileOccupant(Point pt)
   {
      if(pt.withinBounds(this))
      {
         return this.occupancy.getCell(pt);
      }
   }

   public List<Entity> getEntities()
   {
      return this.entities;
   }
}