/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;

import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * This class wrapps a jackson 2 serializer so it can be used by jackson 1 classes.
 * 
 * IMPORTANT: this class assumes that all parameters are wrappers for jackson 2 instances. An exception will be thrown
 * otherwise.
 * 
 * @author abaetz
 * 
 */
public class JsonDeserializer2To1Wrapper<T> extends org.codehaus.jackson.map.JsonDeserializer<T> {

	private JsonDeserializer<T> wrappedSerializer;

	/**
	 * @param wrappedSerializer
	 */
	public JsonDeserializer2To1Wrapper(JsonDeserializer<T> wrappedSerializer) {
		super();
		this.wrappedSerializer = wrappedSerializer;
	}

	/**
	 * @return the wrappedSerializer
	 */
	public JsonDeserializer<T> unwrap() {
		return wrappedSerializer;
	}

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
