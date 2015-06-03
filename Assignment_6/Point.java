public class Point
{
   public final int x;
   public final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }
	public int x()
	{
		return this.x;
	}

	public int y()
	{
		return this.y;
	}

   public boolean equals(Point pt)
   {
      return this.x() == pt.x() && this.y() == pt.y();
   }
}
