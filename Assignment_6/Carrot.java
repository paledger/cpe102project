import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Carrot
   extends Actor
{
   public Carrot(String name, Point position, int rate, List<PImage> imgs)
   {
      super(name, position, rate, imgs);
   }

   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
      };
      return action[0];
   }
}
