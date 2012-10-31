/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.ObjectCodec;

/**
 * This class wrapps a jackson 2 serialization provider so it can be used by jackson 1 classes.
 * 
 * IMPORTANT: all methods that rely on other jackson 1 classes are not supported and will result in an exception
 * 
 * @author abaetz
 */
public class JsonGenerator2To1Wrapper extends JsonGenerator {

	private com.fasterxml.jackson.core.JsonGenerator wrappedGenerator;

	public JsonGenerator2To1Wrapper(com.fasterxml.jackson.core.JsonGenerator wrappedGenerator) {
		this.wrappedGenerator = wrappedGenerator;
	}

	/**
	 * @return the wrappedGenerator
	 */
	public com.fasterxml.jackson.core.JsonGenerator unwrap() {
		return wrappedGenerator;
	}

	@Override
	public JsonGenerator enable(Feature f) {
		wrappedGenerator.enable(com.fasterxml.jackson.core.JsonGenerator.Feature.valueOf(f.name()));
		return this;
	}

	@Override
	public JsonGenerator disable(Feature f) {
		wrappedGenerator.disable(com.fasterxml.jackson.core.JsonGenerator.Feature.valueOf(f.name()));
		return this;
	}

	@Override
	public boolean isEnabled(Feature f) {
		return wrappedGenerator.isEnabled(com.fasterxml.jackson.core.JsonGenerator.Feature.valueOf(f.name()));
	}

	@Override
	public JsonGenerator useDefaultPrettyPrinter() {
		wrappedGenerator.useDefaultPrettyPrinter();
		return this;
	}

	@Override
	public void writeStartArray() throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeStartArray();
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeEndArray() throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeEndArray();
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeStartObject() throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeStartObject();
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}

	}

	@Override
	public void writeEndObject() throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeEndObject();
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeFieldName(String name) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeFieldName(name);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeString(String text) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeString(text);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeString(char[] text, int offset, int len) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeString(text, offset, len);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRawUTF8String(text, offset, length);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeUTF8String(byte[] text, int offset, int length) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeUTF8String(text, offset, length);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRaw(String text) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRaw(text);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRaw(String text, int offset, int len) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRaw(text, offset, len);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRaw(char[] text, int offset, int len) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRaw(text, offset, len);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRaw(char c) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRaw(c);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRawValue(String text) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRawValue(text);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRawValue(String text, int offset, int len) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRawValue(text, offset, len);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeRawValue(char[] text, int offset, int len) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeRawValue(text, offset, len);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(int v) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNumber(v);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(long v) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNumber(v);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(BigInteger v) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNumber(v);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(double d) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNumber(d);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(float f) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNumber(f);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(BigDecimal dec) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNumber(dec);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNumber(String encodedValue) throws IOException, JsonGenerationException,
			UnsupportedOperationException {
		try {
			wrappedGenerator.writeNumber(encodedValue);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeBoolean(boolean state) throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeBoolean(state);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeNull() throws IOException, JsonGenerationException {
		try {
			wrappedGenerator.writeNull();
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void writeObject(Object pojo) throws IOException, JsonProcessingException {
		try {
			wrappedGenerator.writeObject(pojo);
		} catch (JsonGenerationException e) {
			throw new JsonGenerationException(e);
		}
	}

	@Override
	public void flush() throws IOException {
		wrappedGenerator.flush();
	}

	@Override
	public boolean isClosed() {
		return wrappedGenerator.isClosed();
	}

	@Override
	public void close() throws IOException {
		wrappedGenerator.close();
	}

	@Override
	public void copyCurrentEvent(JsonParser jp) throws IOException, JsonProcessingException {
		wrappedGenerator.copyCurrentEvent(((JsonParser2To1Wrapper) jp).unwrap());
	}

	@Override
	public void copyCurrentStructure(JsonParser jp) throws IOException, JsonProcessingException {
		wrappedGenerator.copyCurrentStructure(((JsonParser2To1Wrapper) jp).unwrap());
	}

	/**
	 * Not supported
	 */
	@Override
	public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException,
			JsonGenerationException {
		throw new RuntimeException();
	}

	/**
	 * Not supported
	 */
	@Override
	public JsonGenerator setCodec(ObjectCodec oc) {
		return this;
	}

	/**
	 * Not supported
	 */
	@Override
	public ObjectCodec getCodec() {
		throw new RuntimeException();
	}

	/**
	 * Not supported
	 */
	@Override
	public void writeTree(JsonNode rootNode) throws IOException, JsonProcessingException {
		throw new RuntimeException();
	}

	/**
	 * Not supported
	 */
	@Override
	public JsonStreamContext getOutputContext() {
		throw new RuntimeException();
	}
}
