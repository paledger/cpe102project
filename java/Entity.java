import java.util.StringJoiner;
import java.util.ArrayList;
import processing.core.*;
import java.util.LinkedList;
import java.util.HashMap;

public abstract class Entity extends PApplet
{
	protected String name;
	protected Point position;
	protected int rate;
	protected LinkedList<PImage> imgs;

	protected Entity(String name, Point position, int rate, LinkedList<PImage> imgs)
	{
        this.name = name;
        this.position = position;
        this.rate = rate;
		  this.imgs = imgs;
	}

	public String getName()
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
	 
	 protected LinkedList<PImage> getImages()
	 {
		 return imgs;
	 }

	 protected void scheduleEntity(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
	 {}

	 protected abstract String entityString()
	 {};

   // protected abstract String entityString();
	 
/*	 protected void entityString(Entity entity)
	 {
		 if (entity instanceof MinerNotFull)
		 {
			 StringJoiner joiner = new StringJoiner(" ")
				 .add("miner")
				 .add(entity.name)
				 .add(Integer.toString(entity.position.x))
				 .add(Integer.toString(entity.position.y))
				 .add(Integer.toString(entity.resourceLimit))
				 .add(Integer.toString(entity.rate))
				 .add(Integer.toString(entity.animationRate));				 					 					 
		   String output = joiner.toString();
			 return output;
		 }
	 }	 	*/
}
