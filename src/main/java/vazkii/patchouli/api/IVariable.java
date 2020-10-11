package vazkii.patchouli.api;

import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

import javax.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Patchouli derivation variable, represented internally as {@link JsonElement}.
 */
public interface IVariable {

	/**
	 * Interpret the JSON of this variable as a given Object class.
	 * Will return {@code null} if this type doesn't have a serializer registered;
	 * might return {@code null} or throw if the input is malformed.
	 * 
	 * @param  clazz the Class of the object you want to convert this to
	 * @return       this variable as an object of that class
	 */
	<T> T as(Class<T> clazz);

	default <T> T as(Class<T> clazz, T def) {
		return unwrap().isJsonNull() ? def : as(clazz);
	};

	/**
	 * Take a look at the underlying {@link JsonElement} for this variable.
	 * 
	 * @return the underlying JSON element for this Variable
	 */
	JsonElement unwrap();

	/**
	 * Get this IVariable as a String.
	 */
	default String asString() {
		return asString("");
	}

	/**
	 * Get this IVariable as a String, with a default value.
	 */
	default String asString(String def) {
		return unwrap().isJsonNull() ? def : unwrap().getAsString();
	}

	/**
	 * Get this IVariable as a Number.
	 */
	default Number asNumber() {
		return asNumber(0);
	}

	/**
	 * Get this IVariable as a Number, with a default value.
	 */
	default Number asNumber(Number def) {
		return unwrap().isJsonNull() ? def : unwrap().getAsNumber();
	}

	/**
	 * Get this IVariable as a boolean.
	 */
	default boolean asBoolean() {
		return asBoolean(false);
	}

	/**
	 * Get this IVariable as a boolean, with a default value.
	 * For legacy reasons, the strings {@code ""} and {@code "false"} evaluate to false.
	 */
	default boolean asBoolean(boolean def) {
		return unwrap().isJsonNull() ? def : (!unwrap().getAsString().equals("false") && !unwrap().getAsString().isEmpty() && unwrap().getAsBoolean());
	}

	/**
	 * Get this IVariable as a {@code Stream<IVariable>}, assuming it's backed by a JsonArray.
	 */
	default Stream<IVariable> asStream() {
		return Streams.stream(unwrap().getAsJsonArray()).map(IVariable::wrap);
	}

	/**
	 * Get this IVariable as a {@code List<IVariable>}, returning as singleton if it's not a JsonArray.
	 */
	default Stream<IVariable> asStreamOrSingleton() {
		return unwrap().isJsonArray() ? asStream() : Stream.of(this);
	}

	/**
	 * Get this IVariable as a {@code List<IVariable>}, assuming it's backed by a JsonArray.
	 */
	default List<IVariable> asList() {
		return asStream().collect(Collectors.toList());
	}

	/**
	 * Get this IVariable as a {@code List<IVariable>}, returning as singleton if it's not a JsonArray.
	 */
	default List<IVariable> asListOrSingleton() {
		return asStreamOrSingleton().collect(Collectors.toList());
	}

	/**
	 * Convenience method to create an IVariable from {@link VariableHelper#createFromObject}.
	 */
	static <T> IVariable from(@Nullable T object) {
		return object != null ? VariableHelper.instance().createFromObject(object) : empty();
	}

	/**
	 * Convenience method to create an IVariable from a JsonElement with {@link VariableHelper#createFromJson}.
	 */
	static IVariable wrap(@Nullable JsonElement elem) {
		return elem != null ? VariableHelper.instance().createFromJson(elem) : empty();
	}

	/**
	 * Convenience method to create an IVariable from a list of IVariables.
	 */
	static IVariable wrapList(Iterable<IVariable> elems) {
		JsonArray arr = new JsonArray();
		for (IVariable v : elems) {
			arr.add(v.unwrap());
		}
		return wrap(arr);
	}

	static IVariable wrap(@Nullable Number n) {
		return n != null ? wrap(new JsonPrimitive(n)) : empty();
	}

	static IVariable wrap(@Nullable Boolean b) {
		return b != null ? wrap(new JsonPrimitive(b)) : empty();
	}

	static IVariable wrap(@Nullable String s) {
		return s != null ? wrap(new JsonPrimitive(s)) : empty();
	}

	static IVariable empty() {
		return wrap(JsonNull.INSTANCE);
	}

	static class Serializer implements JsonDeserializer<IVariable> {
		@Override
		public IVariable deserialize(JsonElement elem, Type t, JsonDeserializationContext c) {
			return IVariable.wrap(elem);
		}
	}
}
