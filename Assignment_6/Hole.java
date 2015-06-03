import processing.core.*;
import java.util.List;

public class Hole extends PApplet
{
   public static void createHole(WorldModel world, int mouseX, int mouseY, ImageStore imageStore)
   {
      int ptX = floor(mouseX/32);
      int ptY = floor(mouseY/32);
		Point pt1 = new Point(ptX, ptY);
		Point pt2 = new Point(ptX+1, ptY);
		Point pt3 = new Point(ptX, ptY+1);
		Point pt4 = new Point(ptX+1, ptY+1);
		if(ptX<world.getNumCols() && ptX >= 0 && ptY<world.getNumRows() && ptY >= 0)
		{
			if(world.hole[ptY][ptX]==false&&world.hole[pt2.y()][pt2.x()]==false
				&&world.hole[pt3.y()][pt3.x()]==false&&world.hole[pt4.y()][pt4.x()]==false)
			{
				if(ptX<world.getNumCols()-1 && ptY<world.getNumRows()-1){
					drawHole(world, imageStore, pt1, pt2, pt3, pt4);
					implementHole(world, pt1, pt2, pt3, pt4);
					createBunny(world, pt1, imageStore);
				}
			}
		}	
   }
	
	public static void drawHole(WorldModel world, ImageStore imageStore,
		 Point pt1, Point pt2, Point pt3, Point pt4)
	{
      world.setBackground(pt1, createHoleBackground(imageStore, "hole1"));
      world.setBackground(pt2, createHoleBackground(imageStore, "hole2"));
      world.setBackground(pt3, createHoleBackground(imageStore, "hole3"));
      world.setBackground(pt4, createHoleBackground(imageStore, "hole4"));
		world.hole[pt1.y()][pt1.x()] = true;
		world.hole[pt2.y()][pt2.x()] = true;
		world.hole[pt3.y()][pt3.x()] = true;
		world.hole[pt4.y()][pt4.x()] = true;
		
	}
	
	public static void implementHole(WorldModel world, Point pt1, Point pt2, Point pt3, Point pt4)
	{
		genocide(world, pt1);
		genocide(world, pt2);
		genocide(world, pt3);
		genocide(world, pt4);
	}
	
	public static void createBunny(WorldModel world, Point pt, ImageStore imageStore)
	{
		Point newPt = new Point(pt.x()+4 , pt.y());
		//bunny.scheduleAction(world, bunny, bunny.createAction(world, imageStore), (long)0);
      Actor entity = new Bunny("bunny",newPt,1,6,imageStore.get("bunny"));
      world.addEntity(entity);
      entity.schedule(world, entity.getRate(), imageStore);
	}
	
   private static Background createHoleBackground(ImageStore imageStore, String holenum)
   {
      List<PImage> bgndImgs = imageStore.get(holenum);
      return new Background(holenum, bgndImgs);
   }
	
	public static boolean backgroundAt(WorldModel world, Point pt)
	{
		if(world.withinBounds(pt))
		{
			return world.hole[pt.y()][pt.x()];
		}
		return false;
	}
	
	public static void genocide(WorldModel world, Point pt)
	{
		//if a hole is created somewhere that an entity or obstacle occupies, murder it.
		if(world.isOccupied(pt))
		{
			world.removeEntityAt(pt);
		}
	}
	
}