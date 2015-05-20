import java.util.List;

public class ActionTimePair extends Pair
{
   private Object action;
	private int time;

   public ActionTimePair(Object action, int time)
   {
   	this.action = action;
		this.time = time;
   }

   public Object getAction()
   {
      return this.action;
   }

   public int getTime()
   {
      return this.time;
   }
}