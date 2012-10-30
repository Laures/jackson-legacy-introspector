/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * This class wrapps a jackson 1 serializer so it can be used by jackson 2 classes.
 * 
 * @author abaetz
 */
public class JsonDeserializer1To2Wrapper<T> extends JsonDeserializer<T> {

	private org.codehaus.jackson.map.JsonDeserializer<T> wrappedSerializer;

	/**
	 * @param wrappedSerializer
	 */
	public JsonDeserializer1To2Wrapper(org.codehaus.jackson.map.JsonDeserializer<T> wrappedSerializer) {
		super();
		this.wrappedSerializer = wrappedSerializer;
	}

	/**
	 * @return the wrappedSerializer
	 */
	public org.codehaus.jackson.map.JsonDeserializer<T> unwrap() {
		return wrappedSerializer;
	}

	@Override
	public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return wrappedSerializer.deserialize(new JsonParser2To1Wrapper(jp), new DeserializationContext2to1Wrapper(ctxt));
	}

}
