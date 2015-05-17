import java.util.ArrayList;
import processing.core.*;

public abstract class Entity
{
	protected String name;
	protected Point position;
	protected int rate;
	protected ArrayList<PImage> imgs;

	protected Entity(String name, Point position, int rate, ArrayList<PImage> imgs)
	{
        this.name = name;
        this.position = position;
        this.rate = rate;
		  this.imgs = imgs;
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
	 
	 protected ArrayList<PImage> getImages()
	 {
		 return imgs;
	 }
   // protected abstract String entityString();
}
