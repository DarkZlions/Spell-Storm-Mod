package ch.darklions888.SpellStorm.util.helpers.MathHelpers;

import java.util.ArrayList;
import java.util.List;


/*
 * 
 * To calculate a the coordinates on a cirlce
 * 
 */
public class CircleCalculatorHelper 
{
	public static List<Vec3> getCircleCoordinates(double radius, Vec3 position, int precision, boolean vertical, boolean flip)
	{
		List<Vec3> coordinates = new ArrayList<Vec3> ();
		
		int precise = precision;
		
		double x = position.X();
		double y = position.Y();
		double z = position.Z();

		double r = radius;
		
		double w = 360 / (r * precise);
		
		for(int i = 0; i < (r * precise); i++)
		{
			double a = 0 + w * i;	//Angle alpha
			double b = 360 - w * i; //Angle beta
			
			a = Math.toRadians(a);
			b = Math.toRadians(b);
			
			Vec3 coordinate = new Vec3
					(!flip ? x - (r * Math.cos(a)) : x,
					vertical ? y - (r * Math.sin(b)) : y,
					!flip ? !vertical ? z - (r * Math.sin(b)) : z : z + (r * Math.cos(a)));
			
			coordinates.add(coordinate);
		}
		
		return coordinates;
	}
}