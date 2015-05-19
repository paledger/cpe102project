import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.*;
import java.util.function.*;

public class Vein
	extends ResourceDistance
{
	private static final int VEIN_SPAWN_DELAY = 500;
	private static final int VEIN_RATE_MIN = 8000;
	private static final int VEIN_RATE_MAX = 17000;	
	private static final int ORE_CORRUPT_MIN = 20000;
	private static final int ORE_CORRUPT_MAX = 30000;
	
	public Vein(String name, int rate, Point position, ArrayList<PImage> imgs, 
		int resourceDistance)
	{
 		super(name, position, rate, imgs, resourceDistance);	
		this.resourceDistance = 1;	
	}
	
	public void scheduleEntity(WorldModel world, List<String> iStore)
	{
		this.scheduleVein(world,0,iStore);
	}
	
	public void scheduleVein(WorldModel world, int ticks, List<String> iStore)
	{
		world.scheduleAction(this,this.createVeinAction(world,iStore),ticks+this.rate);
	}
	
	public Object createVeinAction(WorldModel world, List<String> iStore)
	{
		Function<Integer, List<Point>> action = (currentTicks) ->
		{
			this.removePendingAction(action);
			Point pt = this.position;
			Point openPt = (pt.findOpenAround(world,this.resourceDistance));
			List<Point> points = new LinkedList<Point>();
			if (openPt!=null)
			{
				Ore ore = this.createOre(world, "ore - "+this.name+" - "+Integer.toString(currentTicks),
					openPt, currentTicks, iStore);
				world.addEntity(ore);
				points.add(openPt);
			}
			world.scheduleAction(this,this.createVeinAction(world,iStore),
				currentTicks+this.rate);
			return points;
		};
		return action;
	}
	
	public Ore createOre(WorldModel world, String name, Point pt, int ticks, List<String> iStore)
	{
		Ore ore = new Ore(name, pt, ImageStore.getImages(iStore, "ore"),
			random(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX));
		ore.scheduleOre(world, ticks, iStore);
		return ore;
	}
	//missing schedule_entity, schedule_vein, create_vein_action
	//missing create_ore--deals with the i_store
	//missing currentImg
}