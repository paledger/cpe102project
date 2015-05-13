import java.util.ArrayList;
import processing.core.*;

public abstract class Entity
{
	protected String name;
	protected Point position;
	protected int rate;

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
	 
	 protected PImage getImage()
	 {
		 return imgs[this.currentImg];
	 }
	 
	 protected void nextImage()
	 {
		 this.currentImg = (this.currentImg+1) % imgs.length;
	 }
	 
   // protected abstract String entityString();
}
