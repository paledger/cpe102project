import java.util.ArrayList;
import processing.core.*;

public class Obstacle
	extends Entity
{
	public Obstacle(String name, Point position, int rate, ArrayList<PImage> imgs)
	{
		super(name, position, 0, imgs);
	}
}