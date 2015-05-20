import java.util.ArrayList;
import java.util.LinkedList;
import processing.core.*;

public class ResourceDistance
    extends Actionable
{
  	protected int resourceDistance;

    public ResourceDistance(String name, 
    	                     Point position, 
    	                     int rate,
								  	LinkedList<PImage> imgs,
								   int resourceDistance)
    {
    	super(name, position, rate, imgs);	
    	this.resourceDistance = resourceDistance;
    }

    protected int getResourceDistance()
    {
    	return this.resourceDistance;
    }
}