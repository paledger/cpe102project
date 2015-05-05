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
	 
	 //Missing imgs, get_images, get_image, next_image
}
