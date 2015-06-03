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
			if(world.hole[ptY][ptX]==false&&world.hole[ptY][ptX+1]==false
				&&world.hole[ptY+1][ptX]==false&&world.hole[ptY+1][ptX+1]==false)
			{
				if(ptX<world.getNumCols()-1 && ptY<world.getNumRows()-1){
			      world.setBackground(pt1, createHoleBackground(imageStore, "hole1"));
			      world.setBackground(pt2, createHoleBackground(imageStore, "hole2"));
			      world.setBackground(pt3, createHoleBackground(imageStore, "hole3"));
			      world.setBackground(pt4, createHoleBackground(imageStore, "hole4"));
					world.hole[ptY][ptX] = true;
					world.hole[ptY][ptX+1] = true;
					world.hole[ptY+1][ptX] = true;
					world.hole[ptY+1][ptX+1] = true;
				}
			}
		}	
   }
   private static Background createHoleBackground(ImageStore imageStore, String holenum)
   {
      List<PImage> bgndImgs = imageStore.get(holenum);
      return new Background(holenum, bgndImgs);
   }
	
}