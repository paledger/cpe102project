public class WorldModel
{
   public WorldModel(int num_rows, int num_cols, Background background)
   {
      this.background = background;
      this.num_rows = num_rows;
      this.num_cols = num_cols;
      this.occupancy = occ_grid.Grid(num_cols, num_rows, None);
      this.entities = [];
      this.action_queue = ordered_list.OrderedList(); 
   }

   private void handle_timer_event(WorldView view)
   {
      rects = update_on_time(pygame.time.get_ticks());
      view.update_view_tiles(rects);
   }

   private Entity find_nearest(Point pt, Entity type)
   {
         
   }
}