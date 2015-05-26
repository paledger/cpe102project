import java.util.ArrayList;

public class Grid
{
    protected int width;
    protected int height;
    protected Node<Id>[] cells;

	public Grid(int width, int height)
	{
		this.width = width;
		this.height = height;
      this.cells = new Node[width*height];
	}

	protected void createCells()
	{
		for(int row = 0; row < this.height; row ++)
		{
            for(int col = 0; col < this.width; col ++)
            {
            	cells[row*width + col] = new Node<Id>(null,null);
            }
		}
	}

    public void setCell(Point pt, Node<Id> val)
    {
    	this.cells[pt.y()*this.width + pt.x()] = val;
    }

    public Node<Id> getCell(Point pt)
    {
    	return this.cells[pt.y()*this.width + pt.x()];
    }

}