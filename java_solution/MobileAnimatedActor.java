import processing.core.PImage;
import java.util.List;
import static java.lang.Math.abs;
import java.util.LinkedList;
import java.lang.IndexOutOfBoundsException;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   protected Point nextPosition(WorldModel world, Point dest_pt)
   {
      int horiz = Integer.signum(dest_pt.x - getPosition().x);
      Point new_pt = new Point(getPosition().x + horiz, getPosition().y);

      if (horiz == 0 || !canPassThrough(world, new_pt))
      {
         int vert = Integer.signum(dest_pt.y - getPosition().y);
         new_pt = new Point(getPosition().x, getPosition().y + vert);

         if (vert == 0 || !canPassThrough(world, new_pt))
         {
            new_pt = getPosition();
         }
      }

      return new_pt;
   }

   protected Point nextAssPosition(WorldModel world, Point dest_pt)
   {
      System.out.print(" Got da ass. "); //DEBUGGING

      Point start = getPosition();

      System.out.print(start); //DEBUGGING

      Astar assPath = new Astar(start, dest_pt, world);

      System.out.print(assPath); //DEBUGGING

      LinkedList<Point> finalPath = assPath.Ass();

      System.out.print(finalPath); //DEBUGGING
      /*
      if(finalPath.size() <= 0)
      {
         throw new IndexOutOfBoundsException("WHAT ARE YOU DOING?");
      }
      */
      Point newPt = finalPath.get(0);
		System.out.print(finalPath);
      System.out.print(finalPath.size());
      return newPt;
   }

   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
