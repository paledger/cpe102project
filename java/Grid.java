import java.util.ArrayList;

public class Grid
{
    protected int width;
    protected int height;
    protected Object occupancy_value;
    protected Object[] cells;

	public Grid(int width, int height, Object occupancy_value)
	{
		this.width = width;
		this.height = height;
		this.occupancy_value = occupancy_value;
      this.cells = new Object[width*height];
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

    public void setCell(Point pt, Object val)
    {
    	this.cells[pt.y()*this.width + pt.x()] = val;
    }

    public Object getCell(Point pt)
    {
    	return this.cells[pt.y()*this.width + pt.x()];
    }

}