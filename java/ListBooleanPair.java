import java.util.List;

public class ListBooleanPair extends Pair
{
   private List<Point> list;
	private boolean bool;

   public ListBooleanPair(List<Point> list, boolean bool)
   {
   	this.list = list;
		this.bool = bool;
   }

   public List<Point> getEnt()
   {
      return this.list;
   }

   public boolean getBool()
   {
      return this.bool;
   }
}