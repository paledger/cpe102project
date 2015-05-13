import java.util.ArrayList;
import processing.core.*;

public class Actionable
    extends Entity
{
	protected int currentImg;
   public Actionable(String name, Point position,int rate, ArrayList<PImage> imgs)
   {
   	super(name, position,rate, imgs);
		this.currentImg = 0;
   }
	
  protected PImage getImage()
  {
	 return imgs.get(this.currentImg);
  }
 
  protected void nextImage()
  {
	 this.currentImg = (this.currentImg+1) % imgs.size();
  }
	 
	 //missing self.pending_actions[], add_pending_action, get_pending_actions
	 //remove_pending_actions, clear_pending_actions
}