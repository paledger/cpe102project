public class DistPair extends Pair
{
   protected Entity entity;
   protected double dist;

   public DistPair(Entity entity, double dist)
   {
      this.entity = entity;
      this.dist = dist;
   }

   public Entity getEnt()
   {
      return this.entity;
   }

   public double getDist()
   {
      return this.dist;
   }
}