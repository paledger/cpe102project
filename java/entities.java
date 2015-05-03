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
    	super(pending_actions);
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

    public Animated(int animationRate)
    {
    	super(pending_actions);
        this.animationRate = animationRate;
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
   