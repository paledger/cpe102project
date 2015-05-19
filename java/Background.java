import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Background
{
	protected String name;
	protected ArrayList<PImage> imgs;
	protected int currentImg;

	public Background(String name, ArrayList<PImage> imgs)
	{
		this.name = name;
		this.imgs = imgs;
		this.currentImg = 0;
	}
	
	protected ArrayList<PImage> getImages()
	{
		return imgs;
	}
	
	protected PImage getImage()
	{
		return imgs.get(this.currentImg);
	}
	protected void scheduleEntity(WorldModel world, List<String> iStore)
	{
	}
}