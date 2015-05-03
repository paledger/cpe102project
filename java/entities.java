import java.lang.Math;
import java.random;
import org.w3c.dom.Node;

public class Data
{
	public static final int BLOB_RATE_SCALE = 4;
   public static final int BLOB_ANIMATION_RATE_SCALE = 50;
	public static final int BLOB_ANIMATION_MIN = 1;
	public static final int BLOB_ANIMATION_MAX = 3;

	public static final int ORE_CORRUPT_MIN = 20000;
	public static final int ORE_CORRUPT_MAX = 30000;

	public static final int QUAKE_STEPS = 10;
	public static final int QUAKE_DURATION = 1100;
	public static final int QUAKE_ANIMATION_RATE = 100;

	public static final int VEIN_SPAWN_DELAY = 500;
	public static final int VEIN_RATE_MIN = 8000;
	public static final int VEIN_RATE_MAX = 17000;
}

public Interface Resource
{
	void setResourceCount(int n);
	int getResourceCount();
	int getResourceLimit();
}

public class Background
{
	private String name;
	private List imgs;

	public Background(String name, List imgs)
	{
		this.name = name;
		this.imgs = imgs;
		this.currentImg = 0;
	}

    protected Image getImages()
    {
    	return imgs;
    }

    protected Image getImage()
    {
    	return imgs[currentImg];
    }
}

public abstract class Entity
{
	private String name;
	private Point position;
	private List imgs;
	private int rate;
	private int currentImg;

	protected Entity(String name, Point position, List imgs, int rate)
	{
        this.name = name;
        this.position = position;
        this.imgs = imgs;
        this.rate = rate;
		  this.currentImg = currentImg;
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

    protected void nextImage()
    {
      this.currentImg = (this.currentImg + 1) % len(imgs);
    }

    protected abstract String entityString();
}

public class Actionable
    extends Entity
{
	private List pendingActions = new ArrayList<>;

    public Actionable(String name, Point position, List imgs, int rate)
    {
    	super(name, position, imgs, rate);
    }

	protected getPendingActions()
	{
		if (this.hasAttribute("pending_actions"))
		{
			return this.pendingActions;
		}  
		else
		{
			return [];
	    }
	}
	// what the fuck are actions
	protected addPendingAction(Type action)
	{
		if (this.hasAttribute("pending_actions"))
		{
			this.pending_actions.append(action);
		} 
	}

	protected removePendingAction(Type action)
	{
		if (this.hasAttribute("pending_actions"))
		{
			this.pending_actions.remove(action);
		} 
	}

	protected clearPendingActions()
	{
		if (this.hasAttribute("pending_actions"))
		{
			this.pending_actions = [];
		}
	}
}


public class ResourceDistance
    extends Actionable
{
  	 private int resourceDistance;

    public ResourceDistance(String name, 
    	                    Point position, 
    	                    List imgs, 
    	                    int rate,
								  int resourceDistance)
    {
    	super(name, position, imgs, rate);	
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
    	            List imgs, 
    	            int rate,
						int animationRate)
    {
      super(name, position, imgs, rate);
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
	private int resourceLimit;
	private int resourceCount;
	
	public Miner(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animationRate)
	{
		super(name, point, imgs, rate, animationRate);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
	}
}

public class MinerNotFull
	extends Miner
{	
	public MinerFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animationRate)
	{
		this.currentImg = 0;
		this.resourceCount = 0;
		super(name, resourceLimit, position, rate, imgs, 
			animation_rate);		
	}
}

public class MinerFull
	extends Miner
{	
	public MinerFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animationRate)
	{
		this.currentImg = 0;
		this.resourceCount = resourceLimit;
		super(name, resourceLimit, position, rate, imgs, 
			animation_rate);				
	}
}

public class Vein
	extends ResourceDistance
{	
	public Vein(String name, int rate, Point position, 
					List imgs, int resourceDistance)
	{
		this.currentImg = 0;
		this.resourceDistance = 1;
 		super(name, rate, position, imgs, resourceDistance);		
		
	}
}

public class Ore
	extends Actionable
{	
	public Ore(String name, Point position, List imgs, int rate)
		
	{
		this.rate = 5000;
		super(name, position, imgs, this.rate);
	}
}

public class Blacksmith
	extends ResourceDistance
	implements Resource
{
	private int resourceLimit;
	private int resourceCount;
	
	public Blacksmith(String name, Point position, List imgs, 
		int resourceLimit, int rate, int resourceDistance)
	{
		this.resourceDistance = 1;
		this.currentImg= 0;
		this.resourceCount = 0;
		super(name, rate, position, imgs, resourceDistance);
		
	}
}

public class Obstacle
	extends Entity
{
	public Obstacle(String name, Point position, List imgs, int rate)
	{
		this.currentImg = 0;
		this.rate = 0;
		super(name, position, imgs, this.rate);
	}
	
}

public class OreBlob
	extends Animated
{
	public OreBlob(String name, Point position, int rate, 
		List imgs, int animationRate)
	{
		this.currentImg = 0;
		super(name, position, imgs, rate, animationRate);
	}
	
}

public class Quake
	extends Animated
{
	public Quake(String name, Point position, List imgs, int animation_rate)
	{
		this.currentImg = 0;
		this.rate = 0;
		super(name, position, imgs, this.rate, animationRate);
	}
}












   