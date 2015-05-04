public class Ore
	extends Actionable
{	
	private static final int ORE_CORRUPT_MIN = 20000;
	private static final int ORE_CORRUPT_MAX = 30000;
	
	public Ore(String name, Point position, int rate)
		
	{
		super(name, position, 5000);
	}
}