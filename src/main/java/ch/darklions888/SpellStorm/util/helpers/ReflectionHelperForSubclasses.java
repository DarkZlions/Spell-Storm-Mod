package ch.darklions888.SpellStorm.util.helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToAccessFieldException;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToFindFieldException;

public class ReflectionHelperForSubclasses extends ObfuscationReflectionHelper {
	private static final Logger LOGGER = LogManager.getLogger();
	@SuppressWarnings("unused")
	private static final Marker REFLECTION = MarkerManager.getMarker("REFLECTION");
	
    public static <T, E> void setPrivateValue(@Nonnull final Class<? super T> classToAccess, @Nonnull final T instance, @Nullable final E value, @Nonnull final String fieldName)
    {
        try
        {
        	Class<?> subClass = classToAccess;
        	
        	// Set to 10 because I except the classes won't have more than 10 superclasses.
        	for (int i = 0; i < 10; i++) {
        		try {
                    findField(subClass, fieldName).set(instance, value);
                   // break; // If it found the the field it breaks out of the forloop;
        		} catch (Exception e) {
        			subClass = subClass.getSuperclass();
        		}
        	}
        }
        catch (UnableToFindFieldException e)
        {
            LOGGER.error("Unable to locate any field {} on type {}", fieldName, classToAccess.getName(), e);
            throw e;
        }
    }
    
    @SuppressWarnings("serial")
	public static class UnableToAccessFieldException extends RuntimeException
    {
        private UnableToAccessFieldException(Exception e)
        {
            super(e);
        }
    }
}
