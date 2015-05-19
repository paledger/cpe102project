import java.util.ArrayList;
import java.util.LinkedList;
import processing.core.*;

public class Actionable
   extends Entity
{
	protected int currentImg;
	protected ArrayList<Object> pendingActions;
	
   public Actionable(String name, Point position,int rate, ArrayList<PImage> imgs)
   {
   	super(name, position,rate, imgs);
		this.currentImg = 0;
		this.pendingActions = new ArrayList<Object>();
   }
	
   protected PImage getImage()
   {
 	 return imgs.get(this.currentImg);
   }
 
  protected void nextImage()
  {
	 this.currentImg = (this.currentImg+1) % imgs.size();
  }
  
  protected ArrayList<Object> getPendingActions()
  {
	  if (this instanceof Actionable)
	  {
		  return this.pendingActions;
	  } else 
	  {
		  return new ArrayList<Object>();
	  }
  }
  
  protected void addPendingAction(Object action)
  {
	  if (this instanceof Actionable)
	  {
		  this.pendingActions.add(action);
	  }
  }
  
  protected void removePendingAction(Object action)
  {
	  if (this instanceof Actionable)
	  {
		  this.pendingActions.remove(action);
	  }
  }
  
  protected void clearPendingActions()
  {
	  if (this instanceof Actionable)
	  {
		  this.pendingActions = new ArrayList<Object>();
	  }
  }
}
