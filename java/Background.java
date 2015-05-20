import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.*;
import java.util.HashMap;

public class Background
{
	protected String name;
	protected LinkedList<PImage> imgs;
	protected int currentImg;

	public Background(String name, LinkedList<PImage> imgs)
	{
		this.name = name;
		this.imgs = imgs;
		this.currentImg = 0;
	}
	
	protected LinkedList<PImage> getImages()
	{
		return imgs;
	}
	
	protected PImage getImage()
	{
		return imgs.get(this.currentImg);
	}
	
	protected void scheduleEntity(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
	{
	}
}