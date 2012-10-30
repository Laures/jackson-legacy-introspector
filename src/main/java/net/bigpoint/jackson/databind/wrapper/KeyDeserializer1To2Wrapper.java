/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

/**
 * @author Alexander
 * 
 */
public class KeyDeserializer1To2Wrapper extends KeyDeserializer {

	private org.codehaus.jackson.map.KeyDeserializer wrappedDeserializer;

	public KeyDeserializer1To2Wrapper(org.codehaus.jackson.map.KeyDeserializer wrappedDeserializer) {
		this.wrappedDeserializer = wrappedDeserializer;
	}

	/**
	 * @return the wrappedDeserializer
	 */
	public org.codehaus.jackson.map.KeyDeserializer unwrap() {
		return wrappedDeserializer;
	}

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return wrappedDeserializer.deserializeKey(key, new DeserializationContext2to1Wrapper(ctxt));
	}

}
