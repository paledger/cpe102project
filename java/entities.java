// entities.py in Java
// Paula's parts

import java.lang.Math;
import java.random;

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


// CONSIDERING MAKING THIS A PARENT CLASS TO ENTITY B/C
//   IT HAS THE SAME FUNCTIONS? BUT I AM NOT SURE...
public class Background
{
	private String name;
	private List imgs;

	public Background(Sting name, List imgs)
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

	protected Entity(String name, Point point, List imgs, int rate)
	{
        this.name = name;
        this.position = position;
        this.imgs = imgs;
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

    protected void nextImage()
    {
        this.currentImg = (this.currentImg + 1) % len(imgs);
    }

    protected abstract String entityString();
}


// STILL NEEDS WORK ?? hasattr ??
// you need to add a super into this function in order for it to initialize
// things into entity
public class Actionable
    extends Entity
{
	public Actionable(ArrayList pending_actions)
	{
		List this.pendingActions = new ArrayList [];
	}

	protected getPendingActions()
	{
		if (hasattr(this, "pending_actions"))
		{
			return this.pendingActions;
		}   //place where second note in hw3notes might be handy
		else
		{
			return [];
	    }
	}

	protected addPendingAction(Type action)
	{
		if hasattr(this, "pending_actions")
		{
			this.pending_actions.append(action);
		} 
	}

	protected removePendingAction(Type action)
	{
		if hasattr(this, "pending_actions")
		{
			this.pending_actions.remove(action);
		} 
	}

	protected clearPendingActions()
	{
		if hasattr(this, "pending_actions")
		{
			this.pending_actions = [];
		}
	}
}


public class ResourceDistance
    extends Actionable
{
	private int resourceDistance;

    public ResourceDistance(int resourceDistance)
    {
    	this.resourceDistance = resourceDistance;
    	super(pending_actions);		
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

    public Animated(int animationRate)
    {
      this.animationRate = animationRate;
    	super(pending_actions);		  
    }

    protected int getAnimationRate()
    {
    	return this.animationRate;
    }
}

public class Miner
    extends Animated
{
	public Miner(String name, 
		         int resource_limit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animation_rate)
	{
		super(animationRate);
	}
}

public class Obstacle
	extends Entity
{
	private int current_img;
	private int rate;
	public Obstacle(Strong name, Point position, List imgs)
	{
		this.current_img = 0;
		this.rate = 0;
		super(name, position, imgs, rate);
	}
	
}
/* some of allison's stuff don't here don't mind #################################### 
Still need to add in all the functionality to these classes.*/


public class Ore
	extends Actionable
{
	private int current_img;
	
	public Ore(String name, Point position, List imgs, int rate)
		
	{
		this.rate = 5000;
		this.current_img = 0;
		super(pending_actions);
	}
}

public class MinerNotFull
	extends Miner
{
	private int current_img;
	private int resource_count;
	
	public MinerFull(String name, 
		         int resource_limit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animation_rate)
	{
		this.current_img = 0;
		this.resource_count = 0;
		super(name, resource_limit, position, rate, imgs, animation_rate);		
	}
}

public class MinerFull
	extends Miner
{
	private int current_img;
	private int resource_count;
	
	public MinerFull(String name, 
		         int resource_limit, 
		         Point position,
		         int rate,
		         List imgs,
		         int animation_rate)
	{
		super(name, resource_limit, position, rate, imgs, animation_rate);
		this.current_img = 0;
		this.resource_count = resource_limit;
	}
}

public class Vein
	extends ResourceDistance
{
	private int current_img;
	private int resource_distance;
	
	public Vein(String name, int rate, Point position, 
					List imgs, int resource_distance)
	{
//		super();
		
	}
}











   