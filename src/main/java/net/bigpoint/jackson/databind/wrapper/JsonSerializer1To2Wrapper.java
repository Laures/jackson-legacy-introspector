/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * This class wrapps a jackson 1 serializer so it can be used by jackson 2 classes.
 * 
 * @author abaetz
 */
public class JsonSerializer1To2Wrapper<T> extends JsonSerializer<T> {

	private org.codehaus.jackson.map.JsonSerializer<T> wrappedSerializer;

	/**
	 * @return the wrappedSerializer
	 */
	public org.codehaus.jackson.map.JsonSerializer<T> unwrap() {
		return wrappedSerializer;
	}

	@Override
	public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		wrappedSerializer.serialize(value, new JsonGenerator2To1Wrapper(jgen), new SerializerProvider2To1Wrapper(provider));
	}

}
