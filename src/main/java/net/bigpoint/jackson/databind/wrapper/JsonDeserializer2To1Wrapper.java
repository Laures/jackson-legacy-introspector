/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * This class wrapps a jackson 2 deserializer so it can be used by jackson 1 classes.
 * 
 * IMPORTANT: this class assumes that all parameters are wrappers for jackson 2 instances. An exception will be thrown
 * otherwise.
 * 
 * @author abaetz
 * 
 */
public class JsonDeserializer2To1Wrapper<T> extends org.codehaus.jackson.map.JsonDeserializer<T> {

	private JsonDeserializer<T> wrappedDeserializer;

	/**
	 * @param wrappedDeserializer
	 */
	public JsonDeserializer2To1Wrapper(JsonDeserializer<T> wrappedDeserializer) {
		super();
		this.wrappedDeserializer = wrappedDeserializer;
	}

	/**
	 * @return the wrappedSerializer
	 */
	public JsonDeserializer<T> unwrap() {
		return wrappedDeserializer;
	}

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		com.fasterxml.jackson.core.JsonParser parser;
		com.fasterxml.jackson.databind.DeserializationContext context;
		try {
			parser = ((JsonParser2To1Wrapper) jp).unwrap();
			context = ((DeserializationContext2to1Wrapper) ctxt).unwrap();
		} catch (Exception e) {
			throw new IllegalArgumentException("could not unwrapp deserialization parameter", e);
		}
		try {
			return wrappedDeserializer.deserialize(parser, context);
		} catch (Exception e2) {
			// The only exception class available
			throw new JsonMappingException("wrapped exception", e2);
		}
	}

}
