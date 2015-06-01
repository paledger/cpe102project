public class Id
{
	protected Point pt;
	protected int f;
	protected int g;
	protected int h;
	
	public Id(Point pt, int g, int f, int h)
	{
		this.pt = pt;
		this.f = f;
		this.g = g;
		this.h = h;
		g = 1;
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
	public int getH()
	{
		return this.h;
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
	public void setH(int newh)
	{
		h = newh;
	}	

}
