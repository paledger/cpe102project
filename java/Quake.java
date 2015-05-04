public class Quake
	extends Animated
{
	private static final int QUAKE_STEPS = 10;
	private static final int QUAKE_DURATION = 1100;
	private static final int QUAKE_ANIMATION_RATE = 100;
	
	public Quake(String name, Point position, int animationRate)
	{
		super(name, position, 0, animationRate);
	}
}