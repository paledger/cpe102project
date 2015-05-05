public class Animated
    extends Actionable
{
    protected int animationRate;

    public Animated( 
    	            String name, 
    	            Point position,
    	            int rate,
						int animationRate)
    {
      super(name, position, rate);
      this.animationRate = animationRate;		  
    }

    protected int getAnimationRate()
    {
    	return this.animationRate;
    }
}
