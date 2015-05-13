import java.util.ArrayList;
import processing.core.*;

public class Vein
	extends ResourceDistance
{
	private static final int VEIN_SPAWN_DELAY = 500;
	private static final int VEIN_RATE_MIN = 8000;
	private static final int VEIN_RATE_MAX = 17000;	
	
	public Vein(String name, int rate, Point position, ArrayList<PImage> imgs, 
		int resourceDistance)
	{
 		super(name, position, rate, imgs, resourceDistance);	
		this.resourceDistance = 1;	
	}
	//missing schedule_entity, schedule_vein, create_vein_action
	//missing create_ore--deals with the i_store
	//missing currentImg
}