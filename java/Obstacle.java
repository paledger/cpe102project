import java.util.LinkedList;
import processing.core.*;

public class Obstacle
	extends Entity
{
	public Obstacle(String name, Point position, int rate, LinkedList<PImage> imgs)
	{
		super(name, position, 0, imgs);
	}
}