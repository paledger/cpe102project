public class Id
{
	protected Point pt;
	protected int f;
	protected int g;
	
	public Id(Point pt, int f, int g)
	{
		this.pt = pt;
		this.f = f;
		this.g = g;
	}
	
	public Point getPt()
	{
		return this.pt;
	}
	public int getF()
	{
		return this.f;
	}
	public int getG()
	{
		return this.g;
	}
	public void setPt(Point newpt)
	{
		pt = newpt;
	}
	public void setG(int newg)
	{
		g = newg;
	}
	public void setF(int newf)
	{
		f = newf;
	}	

}
