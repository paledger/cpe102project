import java.lang.Math;

public class Point
{
	protected int x;
	protected int y;
	private Entity none;

	public Point(int x, int y)
	{
	    this.x = x;
	    this.y = y;
	}

	public int x()
	{
		return this.x;
	}

	public int y()
	{
		return this.y;
	}

	public boolean withinBounds(WorldModel world)
	{
	    return (this.x >= 0 && this.x < world.num_cols()) &&
            (this.y >= 0 && this.y < world.num_rows());
	}

	public boolean isOccupied(WorldModel world)
	{
		if (this.withinBounds(world))
		{
			if (!(world.occupancy().getCell(this) instanceof Entity))
			{
				return true;
			}
			return false;
		} 
		return false;

	}

	public int distanceSq(Point p2)
	{
		return (this.x - p2.x())*(this.x - p2.x()) + (this.y - p2.y())*(this.y - p2.y());
	}

	public boolean adjacent(Point pt2)
	{
		return (this.x == pt2.x() && Math.abs(this.y - pt2.y()) == 1) ||
		    (this.y == pt2.y() && Math.abs(this.x - pt2.x()) == 1);
	}

	public Point nextPosition(WorldModel world, Point destination)
	{
		Sign actions = new Sign();
      int horiz = actions.sign(destination.x() - this.x());
      Point newPt = new Point(this.x() + horiz, this.y);

      if(horiz == 0 || newPt.isOccupied(world))
      {
			int vert = actions.sign(destination.y() - this.x);
        	newPt = new Point(this.x(), this.y() + vert);
        	if (vert == 0 || newPt.isOccupied(world))
        	{
        		newPt = new Point(this.x(), this.y());
        	}
      }
		return newPt;
	}

	public Point findOpenAround(WorldModel world, int distance)
	{
		for(int dy = -distance; dy < (distance + 1); dy ++)
		{
			for(int dx = -distance; dx < (distance + 1); dx ++)
			{
				Point newPt = new Point(this.x + dx, this.y + dy);
				if (newPt.withinBounds(world) && !(newPt.isOccupied(world)))
				{
					return newPt;
				}
			}
		}
		return null;
	}
}
