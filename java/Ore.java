import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.*;
import java.util.function.*;


public class Ore
	extends Actionable
{	
	private static final int ORE_CORRUPT_MIN = 20000;
	private static final int ORE_CORRUPT_MAX = 30000;
	protected static final int BLOB_RATE_SCALE = 4;
   protected static final int BLOB_ANIMATION_RATE_SCALE = 50;
	protected static final int BLOB_ANIMATION_MIN = 1;
	protected static final int BLOB_ANIMATION_MAX = 3;	
	private static int rand;
	
	public Ore(String name, Point position, int rate, ArrayList<PImage> imgs)
		
	{
		super(name, position, 5000, imgs);
	}
	
	public void scheduleEntity(WorldModel world, List<String> iStore)
	{
		this.scheduleOre(world,0,iStore);
	}
	
	public Object createOreTransformAction(WorldModel world, List<String> iStore)
	{
		Function<Integer, List<Point>> action = (currentTicks) ->
		{
			this.removePendingAction(action);
			
			OreBlob blob = this.createBlob(world, this.name+" -- blob",
				this.position, this.rate/BLOB_RATE_SCALE,currentTicks,iStore);
			
			world.removeEntity(this);
			this.addEntity(blob);
			LinkedList<Point> output = new LinkedList<Point>();
			output.add(blob.position);
			return output;
		};
		return action;
	}
	
	public OreBlob createBlob(WorldModel world, String name, Point pt, 
		int rate, int ticks, List<String> iStore)
	{
		rand = random(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX * BLOB_ANIMATION_RATE_SCALE);
		OreBlob blob = new OreBlob(name,pt,rate,ImageStore.getImages(iStore,"blob"), rand);
		blob.scheduleBlob(world,ticks,iStore);
		return blob;
	}
	
	public void scheduleOre(WorldModel world, int ticks, List<String> iStore)
	{
		world.scheduleAction(this.createOreTransformAction(world, iStore, ticks+this.rate));
	}	
}