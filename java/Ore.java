public class Ore
	extends Actionable
{	
	private static final int ORE_CORRUPT_MIN = 20000;
	private static final int ORE_CORRUPT_MAX = 30000;
	
	public Ore(String name, Point position, int rate)
		
	{
		super(name, position, 5000);
	}
	//missing schedule_entity, create_ore_transform_action (deals with actions)
	//missing create_blob--deals with i_store
	//missing schedule_ore -- works wiht action functionality
	// also missing currentImg
	
}