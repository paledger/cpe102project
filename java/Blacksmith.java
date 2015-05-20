import java.util.ArrayList;
import processing.core.*;
import java.util.LinkedList;

public class Blacksmith
	extends ResourceDistance
	implements Resource
{
	protected int resourceLimit;
	protected int resourceCount;
	
	public Blacksmith(String name, Point position, 
		int resourceLimit, int rate, LinkedList<PImage> imgs, 
			int resourceDistance)
	{
		super(name, position, rate, imgs, resourceDistance);
		this.resourceLimit = resourceLimit;
		this.resourceDistance = 1;
		this.resourceCount = 0;
		
	}
	public void setResourceCount(int n)
	{
		this.resourceCount = n;
	}
	public int getResourceCount()
	{
		return this.resourceCount;
	}
	public int getResourceLimit()
	{
		return this.resourceLimit;
	}
}