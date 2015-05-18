import java.util.ArrayList;

public class Grid
{
    protected int width;
    protected int height;
    protected Entity occupancy_value;
    protected Entity[] cells;

	public Grid(int width, int height, Entity occupancy_value)
	{
		this.width = width;
		this.height = height;
		this.occupancy_value = occupancy_value;
        this.cells = new Entity[width*height];
	}

	protected void createCells()
	{
		for(int row = 0; row < this.height; row ++)
		{
            for(int col = 0; col < this.width; col ++)
            {
            	cells[row*width + col] = occupancy_value;
            }
		}
	}

    public void setCell(Point pt, Entity val)
    {
    	this.cells[pt.y()*this.width + pt.x()] = val;
    }

    public Entity getCell(Point pt)
    {
    	return this.cells[pt.y()*this.width + pt.x()];
    }

}