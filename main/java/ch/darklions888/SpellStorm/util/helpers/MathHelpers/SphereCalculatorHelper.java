package ch.darklions888.SpellStorm.util.helpers.MathHelpers;

import java.util.ArrayList;
import java.util.List;

public class SphereCalculatorHelper 
{
	public static List<Vec3> getSphereCoordinates(double radius, Vec3 position, int precision)
	{
		List<Vec3> coordinates = new ArrayList<Vec3>();
		
		    for (int z = 0; z < radius; z++)
		    {
		        for (int y = 0; y < radius; y++)
		        {
		            for (int x = 0; x < radius; x++)
		            {
		                if (Math.sqrt((float) (x - radius / 2) * ( x - radius / 2) + ( y- radius / 2) * (y - radius / 2) + ( z - radius / 2) * ( z - radius / 2 )) < radius / 2)
		                {
		                	coordinates.add(new Vec3(position.X() + x - (radius / 2), position.Y() + y - (radius / 2), position.Z() + z - (radius / 2)));
		                }
		            }
		        }
		    }
		return coordinates;
	}
}
