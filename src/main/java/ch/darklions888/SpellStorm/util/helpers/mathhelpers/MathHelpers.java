package ch.darklions888.SpellStorm.util.helpers.mathhelpers;

import java.util.List;

import net.minecraft.util.math.BlockPos;

public class MathHelpers 
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
	
	public static double Clamp(double dIn, double min, double max)
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
	
	public static double CycleNumberLine(double value, double min, double max) {
		if (value <= max && value >= min) return value;
		
		if (value > max) return min;
		
		if (value < min) return max;
		
		return 0;
	}
	
	public static List<Vec3> getCircleCoordinates(double radius, Vec3 position, int precision, boolean vertical, boolean flip)
	{
		return CircleCalculatorHelper.getCircleCoordinates(radius, position, precision, vertical, flip);
	}
	
	public static List<Vec3> getCircleCoordinates(double radius, BlockPos position, int precision, boolean vertical, boolean flip)
	{
		return CircleCalculatorHelper.getCircleCoordinates(radius, new Vec3(position.getX(), position.getY(), position.getZ()), precision, vertical, flip);
	}
	
	public static List<Vec3> getSphereCoordinates(double radius, Vec3 position, int precision)
	{
		return SphereCalculatorHelper.getSphereCoordinates(radius, position, precision);
	}
}
