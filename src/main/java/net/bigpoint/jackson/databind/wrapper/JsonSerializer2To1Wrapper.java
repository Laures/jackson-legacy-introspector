/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;

import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * This class wrapps a jackson 2 serializer so it can be used by jackson 1 classes.
 * 
 * IMPORTANT: this class assumes that all parameters are wrappers for jackson 2 instances. An exception will be thrown
 * otherwise.
 * 
 * @author abaetz
 * 
 */
public class JsonSerializer2To1Wrapper<T> extends org.codehaus.jackson.map.JsonSerializer<T> {

	private JsonSerializer<T> wrappedSerializer;

	/**
	 * @param wrappedSerializer
	 */
	public JsonSerializer2To1Wrapper(JsonSerializer<T> wrappedSerializer) {
		super();
		this.wrappedSerializer = wrappedSerializer;
	}

	/**
	 * @return the wrappedSerializer
	 */
	public JsonSerializer<T> unwrap() {
		return wrappedSerializer;
	}

	@Override
	public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		com.fasterxml.jackson.core.JsonGenerator generator;
		com.fasterxml.jackson.databind.SerializerProvider prov;
		try {
			generator = ((JsonGenerator2To1Wrapper) jgen).unwrap();
			prov = ((SerializerProvider2To1Wrapper) provider).unwrap();
		} catch (Exception e) {
			throw new IllegalArgumentException("could not unwrapp serialization parameter", e);
		}
		try {
			wrappedSerializer.serialize(value, generator, prov);
		} catch (Exception e2) {
			// The only exception class available
			throw new JsonMappingException("", e2);
		}
	}
}
