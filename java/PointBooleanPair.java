public class PointBooleanPair extends Pair
{
   protected Point pt;
   protected boolean bool;

   public PointBooleanPair(Point pt, boolean bool)
   {
      this.pt = pt;
      this.bool = bool;
   }

   public Point getPoint()
   {
      return this.pt;
   }

   public boolean getBool()
   {
      return this.bool;
   }
}