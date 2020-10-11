package vazkii.patchouli.api;

import com.google.gson.JsonElement;

/**
 * An instance of a class implementing this interface
 * provides conversions for the given type to/from {@link JsonElement}.
 * 
 * @param <T> the type that gets converted to/from JSON
 */
public interface IVariableSerializer<T> {
	/**
	 * Deserialize an object of type T from JSON.
	 */
	T fromJson(JsonElement json);

	/**
	 * Serialize an object of type T to JSON.
	 */
	JsonElement toJson(T object);
}
