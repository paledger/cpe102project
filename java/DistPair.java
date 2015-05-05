public class DistPair
{
   private Entity entity;
   private double distanceTo;

   public DistPair(Entity entity, double distanceTo)
   {
      this.entity = entity;
      this.distanceTo = distanceTo;
   }

   public Entity getEnt()
   {
      return this.entity;
   }

   public double getDist()
   {
      return this.distanceTo;
   }
}