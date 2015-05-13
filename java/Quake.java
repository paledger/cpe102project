import java.util.ArrayList;
import processing.core.*;

public class Quake
	extends Animated
{
	private static final int QUAKE_STEPS = 10;
	private static final int QUAKE_DURATION = 1100;
	private static final int QUAKE_ANIMATION_RATE = 100;
	
	public Quake(String name, Point position, ArrayList<PImage> imgs, int animationRate)
	{
		super(name, position, 0, imgs, animationRate);
	}
	//missing schedule_entity and schedule_quake
}