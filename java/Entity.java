import java.util.StringJoiner;
import java.util.ArrayList;
import processing.core.*;
import java.util.LinkedList;
import java.util.HashMap;

public abstract class Entity 
	extends Actor
{
	protected String name;
	protected Point position;
	protected int rate;
	protected LinkedList<PImage> imgs;
	protected int currentImg;

	protected Entity(String name, Point position, int rate, LinkedList<PImage> imgs)
	{
		super();
        this.name = name;
        this.position = position;
        this.rate = rate;
		this.imgs = imgs;
		this.currentImg = 0;
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

   protected PImage getImage()
   {
 	 return imgs.get(this.currentImg);
   }
	 
	 protected LinkedList<PImage> getImages()
	 {
		 return imgs;
	 }

	 protected void scheduleEntity(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
	 {
	 	
	 }
}
