import java.util.ArrayList;

public class ListBooleanPair extends Pair
{
   protected ArrayList<Point> entityPt;
   protected boolean bool;

   public ListBooleanPair(ArrayList<Point> entity, boolean bool)
   {
      this.entityPt = entityPt;
      this.bool = bool;
   }

   public ArrayList<Point> getEnt()
   {
      return this.entityPt;
   }

   public boolean getBool()
   {
      return this.bool;
   }
}