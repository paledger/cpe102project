public class Rect 
{
	private int left;
	private int top;
	private int width;
	private int height;

	public Rect(int startX, int startY, int width, int height)
	{
		this.left = startX;
		this.top = startY;
		this.width = width;
		this.height = height;
	}

	public int left()
	{
		return this.left;
	}

	public int top()
	{
		return this.top;
	}

		public int width()
	{
		return this.width;
	}

	public int height()
	{
		return this.height;
	}

	public boolean collidepoint(Point pt)
	{
		return (pt.x() > left && pt.x() < left + width 
			&& pt.y() > top && pt.y() < top + height);
	}

}