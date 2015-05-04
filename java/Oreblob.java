public class OreBlob
	extends Animated
{
	private static final int BLOB_RATE_SCALE = 4;
   private static final int BLOB_ANIMATION_RATE_SCALE = 50;
	private static final int BLOB_ANIMATION_MIN = 1;
	private static final int BLOB_ANIMATION_MAX = 3;
	
	public OreBlob(String name, Point position, int rate, 
		int animationRate)
	{
		super(name, position, rate, animationRate);
	}
	
}