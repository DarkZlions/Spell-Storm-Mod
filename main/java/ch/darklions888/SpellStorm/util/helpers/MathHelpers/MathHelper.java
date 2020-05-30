package ch.darklions888.SpellStorm.util.helpers.MathHelpers;

import java.util.List;

public class MathHelper 
{
	public float[] CalcBiggerNumber(float f1, float f2)
	{
		float b = 0;	//big
		float s = 0;	//smöll
		
		if(f1 >= f2)
		{
			b = f1;
			s = f2;
		}
		else
		{
			b = f2;
			s = f1;
		}
		
		return new float[] {b, s};
	}
	
	public static boolean IfMinusNumber(float f1, float f2)
	{
		if(f1 - f2 < 0)
		{
			return false;
		}
		else if(f1 - f2 > 0)
		{
			return true;
		}
		else
			return false;
	}
	
	public double Clamp(double dIn, double min, double max)
	{
		if(dIn >= min && dIn <= max)
			return dIn;
		else if (dIn <= min && dIn <= max)
			return min;
		else if(dIn >= min && dIn >= max)
			return max;
		else
			return dIn;
	}
	
	public static List<Vec3> getCircleCoordinates(double radius, Vec3 position, int precision, boolean vertical, boolean flip)
	{
		return CircleCalculatorHelper.getCircleCoordinates(radius, position, precision, vertical, flip);
	}
	
	public static List<Vec3> getSphereCoordinates(double radius, Vec3 position, int precision)
	{
		return SphereCalculatorHelper.getSphereCoordinates(radius, position, precision);
	}
}
