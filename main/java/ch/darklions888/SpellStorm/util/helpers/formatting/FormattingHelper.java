package ch.darklions888.SpellStorm.util.helpers.formatting;

import ch.darklions888.SpellStorm.enums.MagicSource;

public class FormattingHelper 
{
	public static String GetSourceColor(MagicSource sourceColor)
	{
		switch(sourceColor)
		{
		case LIGHTMAGIC:
			return "\u00A7e";
		
		case DARKMAGIC:
			return "\u00A74";
			
		case UNKNOWNMAGIC:
			return "\u00A70";
		
		default:
			return "\u00A7f";
		}
	}
	
	public static String GetFontFormat(MagicSource source)
	{
		switch(source) 
		{
		case UNKNOWNMAGIC:
			return "\u00A7k";
			
			default:
				return "\u00A7l";
		}
	}
}
