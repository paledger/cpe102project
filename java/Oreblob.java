import java.util.ArrayList;
import processing.core.*;

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
	//missing schedule_entity
	//create_ore_blob_action uses action stuff
	//create_quake uses i_store
	//schedule_blob deals with scheduling stuff
	
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
	//blob_to_vein returns a function and boolean tuple--can't return functions 
	//just yet, can't use move_entity
}