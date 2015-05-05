import java.lang.Math;

public class Point
{
	private double x;
	private double y;

	public Point(double x, double y)
	{
	    this.x = x;
	    this.y = y;
	}

	public double x()
	{
		return this.x;
	}

	public double y()
	{
		return this.y;
	}

	public boolean withinBounds(WorldModel world)
	{
	    return (this.x >= 0 && this.x < world.num_cols) &&
            (this.y >= 0 && this.y < world.num);
	}

	public boolean isOccupied(WorldModel world)
	{
		return (this.withinBounds(world) && 
			world.occupancy.getCell(this) != Null);
	}

	public double distanceSq(Point p2)
	{
		return (this.x - p2.x())*(this.x - p2.x()) + (this.y - p2.y())*(this.y - p2.y());
	}

	public boolean adjacent(Point pt2)
	{
		return (this.x == pt2.x() && Math.abs(this.y - pt2.y()) == 1) ||
		    (this.y == pt2.y() && Math.abs(this.x - pt2.x()) == 1);
	}
}
