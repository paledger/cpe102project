import java.util.LinkedList;
import processing.core.*;

public class Quake
	extends Animated
{
	private static final int QUAKE_STEPS = 10;
	private static final int QUAKE_DURATION = 1100;
	private static final int QUAKE_ANIMATION_RATE = 100;
	
	public Quake(String name, Point position, LinkedList<PImage> imgs, int animationRate)
	{
		super(name, position, 0, imgs, animationRate);
	}

	public void scheduleEntity(){} // in effect, pass

	public void scheduleQuake(WorldModel world, int ticks)
	{
		world.scheduleAnimation(this, QUAKE_STEPS);
		world.scheduleAction(this, world.createEntityDeathAction(this), 
			ticks + QUAKE_DURATION);
	}
}