import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.*;
import java.util.function.*;
import java.util.HashMap;


public class Ore
	extends Actionable
{	
	protected static final int BLOB_RATE_SCALE = 4;
   protected static final int BLOB_ANIMATION_RATE_SCALE = 50;
	protected static final int BLOB_ANIMATION_MIN = 1;
	protected static final int BLOB_ANIMATION_MAX = 3;	
	private static int rand;
	
	public Ore(String name, Point position, LinkedList<PImage> imgs, int rate)
		
	{
		super(name, position, 5000, imgs);
		this.rate = 5000;
	}
	
	public void scheduleEntity(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
	{
		this.scheduleOre(world,0,iStore);
	}
	
	public Object createOreTransformAction(WorldModel world, HashMap<String, LinkedList<PImage>> iStore)
	{
		LongConsumer[] action = {null};
			action[0] = (long currentTicks)->
			{
				this.removePendingAction(action[0]);
				OreBlob blob = this.createBlob(world, this.name+" -- blob",
					this.position, this.rate/BLOB_RATE_SCALE,(int)currentTicks,iStore);	
				world.removeEntity(this);
				world.addEntity(blob);
				LinkedList<Point> output = new LinkedList<Point>();
				output.add(blob.position);	
			};
		return action;
	}
	
	public OreBlob createBlob(WorldModel world, String name, Point pt, 
		int rate, int ticks, HashMap<String, LinkedList<PImage>> iStore)
	{
		rand = (int)random(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX * BLOB_ANIMATION_RATE_SCALE);
		OreBlob blob = new OreBlob(name,pt,rate,ImageStore.getImages(iStore,"blob"), rand);
		blob.scheduleBlob(world,ticks,iStore);
		return blob;
	}
	
	public void scheduleOre(WorldModel world, int ticks, HashMap<String, LinkedList<PImage>> iStore)
	{
		world.scheduleAction(this, this.createOreTransformAction(world, iStore), ticks+this.rate);
	}	
}