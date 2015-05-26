public class Id
{
	protected Point pt;
	protected double f;
	protected double g;
	
	public Id(Point pt, double f, double g)
	{
		this.pt = pt;
		this.f = f;
		this.g = g;
	}
	
	public Point getPt()
	{
		return this.pt;
	}
	public double getF()
	{
		return this.f;
	}
	public double getG()
	{
		return this.g;
	}
	public void setPt(Point newpt)
	{
		pt = newpt;
	}
	public void setG(double newg)
	{
		g = newg;
	}
	public void setF(double newf)
	{
		f = newf;
	}	

}
