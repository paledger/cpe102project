public class Vein
	extends ResourceDistance
{
	private static final int VEIN_SPAWN_DELAY = 500;
	private static final int VEIN_RATE_MIN = 8000;
	private static final int VEIN_RATE_MAX = 17000;	
	
	public Vein(String name, int rate, Point position, int resourceDistance)
	{
 		super(name, position, rate, resourceDistance);	
		this.resourceDistance = 1;	
	}
}