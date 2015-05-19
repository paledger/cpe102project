import java.util.ArrayList;
import java.util.LinkedList;
import processing.core.*;

public class Animated
    extends Actionable
{
    protected int animationRate;

    public Animated( 
    	            String name, 
    	            Point position,
    	            int rate,
						LinkedList<PImage> imgs,
						int animationRate)
    {
      super(name, position, rate, imgs);
      this.animationRate = animationRate;		  
    }

    protected int getAnimationRate()
    {
    	return this.animationRate;
    }
}
