package ch.darklions888.SpellStorm.util.helpers;

public class CalcHelpers 
{
	public float[] CalcBiggerNumber(float f1, float f2)
	{
		float b = new Float(0);	//big
		float s = new Float(0);	//smöll
		
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
	
	public boolean IfMinusNumber(float f1, float f2)
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
}
