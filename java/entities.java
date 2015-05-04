import java.lang.Math;
import java.random;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import org.w3c.dom.Node;

public interface Resource
{
	void setResourceCount(int n);
	int getResourceCount();
	int getResourceLimit();
}

public class Background
{
	private String name;

	public Background(String name)
	{
		this.name = name;
	}
}

public abstract class Entity
{
	protected String name;
	protected Point position;
	protected int rate;

	protected Entity(String name, Point position, int rate)
	{
        this.name = name;
        this.position = position;
        this.rate = rate;
	}

	protected String getName()
	{
		return name;
	}

    protected void setPosition(Point pt)
    {
    	this.position = pt;
    }

    protected Point getPosition()
    {
    	return position;
    }

    protected int getRate()
    {
    	return rate;
    }
	 
    protected abstract String entityString();
}

public class Actionable
    extends Entity
{
    public Actionable(String name, Point position,int rate)
    {
    	super(name, position,rate);
    }
}


public class ResourceDistance
    extends Actionable
{
  	 protected int resourceDistance;

    public ResourceDistance(String name, 
    	                    Point position, 
    	                    int rate,
								  int resourceDistance)
    {
    	super(name, position, rate);	
    	this.resourceDistance = resourceDistance;
    }

    protected int getResourceDistance()
    {
    	return this.resourceDistance;
    }
}


public class Animated
    extends Actionable
{
    private int animationRate;

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

// need to write the functions for the resource interface here
public class Miner
    extends Animated
	 implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	public Miner(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         int animationRate)
	{
		super(name, position, rate, animationRate);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
	}
}

public class MinerNotFull
	extends Miner
{	
	public MinerNotFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate,
			animationRate);	
		this.resourceCount = 0;	
	}
}

public class MinerFull
	extends Miner
{	
	public MinerFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, 
			animationRate);			
		this.resourceCount = resourceLimit;			
	}
}

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

public class Blacksmith
	extends ResourceDistance
	implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	public Blacksmith(String name, Point position, 
		int resourceLimit, int rate, int resourceDistance)
	{
		super(name, position, rate, resourceDistance);
		this.resourceDistance = 1;
		this.resourceCount = 0;
		
	}
}

public class Obstacle
	extends Entity
{
	public Obstacle(String name, Point position, int rate)
	{
		super(name, position,0);
	}
	
}

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












   