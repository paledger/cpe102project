import java.util.ArrayList;
import processing.core.*;

public class Actionable
    extends Entity
{
    public Actionable(String name, Point position,int rate, ArrayList<PImage> imgs)
    {
    	super(name, position,rate, imgs);
    }
	 
	 //missing self.pending_actions[], add_pending_action, get_pending_actions
	 //remove_pending_actions, clear_pending_actions
}