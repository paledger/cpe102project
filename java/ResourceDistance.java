public class ResourceDistance
    extends Actionable
{
  	protected int resourceDistance;

    public ResourceDistance(String name, 
    	                    Point position, 
    	                    int rate,
							int resourceDistance)
    {
    	super(name, position, rate);	
    	this.resourceDistance = resourceDistance;
    }

    protected int getResourceDistance()
    {
    	return this.resourceDistance;
    }
}