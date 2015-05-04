import java.lang.Math;
import java.random;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
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

public interface Resource
{
	void setResourceCount(int n);
	int getResourceCount();
	int getResourceLimit();
}

public class Background
{
	private String name;
//	private List imgs;

	public Background(String name/*,List imgs*/)
	{
		this.name = name;
//		this.imgs = imgs;
//		this.currentImg = 0;
	}

/*    protected Image getImages()
    {
    	return imgs;
    }

    protected Image getImage()
    {
    	return imgs.get(currentImg);
    }
}
	*/

public abstract class Entity
{
	protected String name;
	protected Point position;
//	protected List imgs;
	protected int rate;

	protected Entity(String name, Point position,/* List imgs, */int rate)
	{
        this.name = name;
        this.position = position;
//        this.imgs = imgs;
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

    /*protected void nextImage()
    {
      this.currentImg = (this.currentImg + 1) % len(imgs);
    }*/

    protected abstract String entityString();
}

public class Actionable
    extends Entity
{
	//private List pendingActions = new LinkedList<Type>();

    public Actionable(String name, Point position,/* List imgs, */int rate)
    {
    	super(name, position, /*imgs, */rate);
    }

	/*protected List getPendingActions()
	{
		if (this.hasAttribute("pending_actions"))
		{
			return this.pendingActions;
		}  
		else
		{
			return new LinkedList<Type>();
	    }
	}
	// what the fuck are actions
	protected void addPendingAction(Type action)
	{
		if (this.hasAttribute("pending_actions"))
		{
			this.pending_actions.append(action);
		} 
	}

	protected void removePendingAction(Type action)
	{
		if (this.hasAttribute("pending_actions"))
		{
			this.pending_actions.remove(action);
		} 
	}

	protected void clearPendingActions()
	{
		if (this.hasAttribute("pending_actions"))
		{
			this.pending_actions = new LinkedList<Type>();
		}
	}*/
}


public class ResourceDistance
    extends Actionable
{
  	 protected int resourceDistance;

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
	protected int currentImg;
	protected int resourceLimit;
	protected int resourceCount;
	
	public Miner(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animationRate)
	{
		super(name, position, imgs, rate, animationRate);
		this.resourceLimit = resourceLimit;
		this.resourceCount = resourceCount;
		this.currentImg = currentImg;
	}
}

public class MinerNotFull
	extends Miner
{	
	public MinerNotFull(String name, 
		         int resourceLimit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, imgs, 
			animationRate);	
		this.currentImg = 0;
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
		         List imgs,
		         int animationRate)
	{
		super(name, resourceLimit, position, rate, imgs, 
			animationRate);			
		this.currentImg = 0;
		this.resourceCount = resourceLimit;			
	}
}

public class Vein
	extends ResourceDistance
{	
	private int currentImg;
	public Vein(String name, int rate, Point position, 
					List imgs, int resourceDistance)
	{
 		super(name, position, imgs, rate, resourceDistance);	
		this.currentImg = 0;
		this.resourceDistance = 1;	
	}
}

public class Ore
	extends Actionable
{	
	public Ore(String name, Point position, List imgs, int rate)
		
	{
		super(name, position, imgs, 5000);
	}
}

public class Blacksmith
	extends ResourceDistance
	implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	private int currentImg;	
	
	public Blacksmith(String name, Point position, List imgs, 
		int resourceLimit, int rate, int resourceDistance)
	{
		super(name, position, imgs, rate, resourceDistance);
		this.resourceDistance = 1;
		this.currentImg= 0;
		this.resourceCount = 0;
		
	}
}

public class Obstacle
	extends Entity
{
	private int currentImg;
	public Obstacle(String name, Point position, List imgs, int rate)
	{
		super(name, position, imgs,0);
		this.currentImg = 0;
	}
	
}

public class OreBlob
	extends Animated
{
	private int currentImg;
	public OreBlob(String name, Point position, int rate, 
		List imgs, int animationRate)
	{
		super(name, position, imgs, rate, animationRate);
		this.currentImg = 0;		
	}
	
}

public class Quake
	extends Animated
{
	private int currentImg;
	public Quake(String name, Point position, List imgs, int animationRate)
	{
		super(name, position, imgs, 0, animationRate);
		this.currentImg = 0;
	}
}












   