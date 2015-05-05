public class OreBlob
	extends Animated
{
	private static final int BLOB_RATE_SCALE = 4;
   private static final int BLOB_ANIMATION_RATE_SCALE = 50;
	private static final int BLOB_ANIMATION_MIN = 1;
	private static final int BLOB_ANIMATION_MAX = 3;
	
	public OreBlob(String name, Point position, int rate, 
		int animationRate)
	{
		super(name, position, rate, animationRate);
	}
	//missing schedule_entity, blob_to_vein has that weird tuple thing,
	//create_ore_blob_action uses action stuff
	//create_quake uses i_store
	//schedule_blob deals with scheduling stuff
	
	/*public Point blobNextPosition(WorldModel world, Point destPt)
	{
		private int horiz = sign(destPt.x - this.position.x);
		Point newPt = new Point(this.position.x+horiz, this.position.y);
		
		if ((horiz==0) || ((newPt.isOccupied(world)) && !(world.getTileOccupant(newPt) instanceof Ore))))
		{
			private int vert = Actions.sign(destPt.y - this.position.y);
			Point newPt = new Point(this.position.x, this.position.y + vert);
			
			if ((vert==0)|| ((newPt.isOccupied(world) && !(world.getTileOccupant(newPt),Ore))))
			{
				Point newPt = new Point(this.position.x,this.position.y);
			}
		}
		return newPt;
	}*/
		
}