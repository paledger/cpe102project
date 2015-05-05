import java.util.ArrayList;

public class Grid
{
    private int width;
    private int height;
    private Entity occupancy_value;
    private ArrayList<Entity> cells;

	public Grid(int width, int height, Entity occupancy_value)
	{
		this.width = width;
		this.height = height;
		this.occupancy_value = occupancy_value;

	}

	private void createCells()
	{
		for(int row = 0; row < this.height; row ++)
		{
            this.cells.addLast();
            for(double col = 0; col < this.width; col ++)
            {
            	this.cells[row].addLast(this.occupancy_value);
            }
		}
	}

    public void setCell(Point pt, Entity val)
    {
    	this.cells[pt.y()][pt.x()] = val;
    }

    public Entity getCell(Point pt)
    {
    	return this.cells[pt.y()][pt.x()];
    }

}