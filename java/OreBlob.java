import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.*;
import java.util.function.*;

public class OreBlob
	extends Animated
{
	protected static final int BLOB_RATE_SCALE = 4;
    protected static final int BLOB_ANIMATION_RATE_SCALE = 50;
	protected static final int BLOB_ANIMATION_MIN = 1;
	protected static final int BLOB_ANIMATION_MAX = 3;
	
	public OreBlob(String name, Point position, int rate, ArrayList<PImage> imgs, 
		int animationRate)
	{
		super(name, position, rate, imgs, animationRate);
	}
	//missing schedule_entity %
	//create_ore_blob_action uses action stuff
	//create_quake uses i_store %
	//schedule_blob deals %
	//blob_to_vein returns a function and boolean tuple

	public Point blobNextPosition(WorldModel world, Point destPt)
	{
		Sign actions = new Sign();
		int horiz = actions.sign(destPt.x()-this.position.x());
		Point newPt = new Point(this.position.x()+horiz,this.position.y());
		
		if (horiz==0 || (newPt.isOccupied(world) && !(world.getTileOccupant(newPt) instanceof Ore)))
		{
			int vert = actions.sign(destPt.y()-this.position.y());
			newPt = new Point(this.position.x(),this.position.y()+vert);
			if (vert==0 || (newPt.isOccupied(world) && !(world.getTileOccupant(newPt) instanceof Ore)))
			{
				newPt = new Point(this.position.x(), this.position.y());
			}
		}
		return newPt;
	}	
	public void scheduleEntity(WorldModel world, List<String> iStore){}

	public Object createOreBlobAction(WorldModel world, List<String> iStore)
	{
		Function<Integer, Object> action = (currentTicks) ->
		{
			this.removePendingAction(action);

			Point entityPt = this.getPosition();
            Vein generalV = new Vein("stand_in", 0, new Point(0, 0), 
            	new ArrayList<PImage>(), 0);

			Entity vein = world.findNearest(entityPt, generalV);
			ListBooleanPair found = this.blobToVein(world, (Vein)vein);
			List<Point> tiles = found.getEnt();

			int nextTime = currentTicks + this.getRate();
			if(found.getBool())
			{
				Quake quake = createQuake(world, tiles.get(0),
					currentTicks, iStore);
				world.addEntity(quake);
				nextTime = currentTicks + getRate()*2;
			}

			world.scheduleAction(this, this.createOreBlobAction(world, iStore), nextTime);
			return tiles;
		};
		return action;
	}

	public ListBooleanPair blobToVein(WorldModel world, Vein v)
	{
		Point entityPt = getPosition();
		if(!v)
		{
			return null;
		}
	}

    // IS ISTORE A LIST OF IMAGES OR A FILE THAT WE ARE READING??
	public Quake createQuake(WorldModel world, Point pt, int ticks, List<String> iStore)
	{
		int QUAKE_ANIMATION_RATE = 100;
		Quake quake = new Quake("quake", pt, ImageStore.getImages(iStore, "quake"), QUAKE_ANIMATION_RATE);
		quake.scheduleQuake(world, ticks);

		return quake;
	} 

	public void scheduleBlob(WorldModel world, int ticks, List<String> iStore)
	{
		world.scheduleAction(this, this.createOreBlobAction(world, 
			iStore), ticks + this.getRate());
		world.scheduleAnimation(this, 0);
	}
}