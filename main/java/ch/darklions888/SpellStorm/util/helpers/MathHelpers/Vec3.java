package ch.darklions888.SpellStorm.util.helpers.MathHelpers;

/*
 * 
 * Class to hold 3 numbers
 * 
 */
public class Vec3 
{
	private double x, y, z;
	
	public Vec3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double X()
	{
		return this.x;
	}
	
	public double Y()
	{
		return this.y;
	}
	
	public double Z()
	{
		return this.z;
	}
}
