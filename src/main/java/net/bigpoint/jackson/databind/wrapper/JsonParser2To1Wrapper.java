/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import static net.bigpoint.jackson.databind.wrapper.JacksonTransformers.wrapJsonParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

import com.fasterxml.jackson.core.io.SerializedString;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.SerializableString;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author Alexander
 * 
 */
public class JsonParser2To1Wrapper extends JsonParser {

	private com.fasterxml.jackson.core.JsonParser wrappedParser;

	/**
	 * @param wrappedParser
	 */
	public JsonParser2To1Wrapper(com.fasterxml.jackson.core.JsonParser wrappedParser) {
		super();
		this.wrappedParser = wrappedParser;
	}

	/**
	 * @return the wrappedParser
	 */
	public com.fasterxml.jackson.core.JsonParser unwrap() {
		return wrappedParser;
	}

	@Override
	public ObjectCodec getCodec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCodec(ObjectCodec c) {
		// TODO Auto-generated method stub

	}

	@Override
	public JsonStreamContext getParsingContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
		return wrappedParser.getBinaryValue(JacksonTransformers.transformBase64Variant(b64variant));
	}

	@Override
	public void close() throws IOException {
		wrappedParser.close();
	}

	@Override
	public JsonToken nextToken() throws IOException, JsonParseException {
		try {
			return JacksonTransformers.transformJsonToken(wrappedParser.nextToken());
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public JsonParser skipChildren() throws IOException, JsonParseException {
		try {
			wrappedParser = wrappedParser.skipChildren();
			return this;
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public boolean isClosed() {
		return wrappedParser.isClosed();
	}

	@Override
	public String getCurrentName() throws IOException, JsonParseException {
		try {
			return wrappedParser.getCurrentName();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public JsonLocation getTokenLocation() {
		com.fasterxml.jackson.core.JsonLocation location = wrappedParser.getTokenLocation();
		return new JsonLocation(location.getSourceRef(), location.getCharOffset(), location.getByteOffset(),
				location.getLineNr(), location.getColumnNr());
	}

	@Override
	public JsonLocation getCurrentLocation() {
		com.fasterxml.jackson.core.JsonLocation location = wrappedParser.getCurrentLocation();
		return new JsonLocation(location.getSourceRef(), location.getCharOffset(), location.getByteOffset(),
				location.getLineNr(), location.getColumnNr());
	}

	@Override
	public String getText() throws IOException, JsonParseException {
		try {
			return wrappedParser.getText();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public char[] getTextCharacters() throws IOException, JsonParseException {
		try {
			return wrappedParser.getTextCharacters();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public int getTextLength() throws IOException, JsonParseException {
		try {
			return wrappedParser.getTextLength();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public int getTextOffset() throws IOException, JsonParseException {
		try {
			return wrappedParser.getTextOffset();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public Number getNumberValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getNumberValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public NumberType getNumberType() throws IOException, JsonParseException {
		try {
			return JacksonTransformers.transformNumberType(wrappedParser.getNumberType());
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public int getIntValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getIntValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public long getLongValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getLongValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getBigIntegerValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public float getFloatValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getFloatValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public double getDoubleValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getDoubleValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public BigDecimal getDecimalValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getDecimalValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public void clearCurrentToken() {
		wrappedParser.clearCurrentToken();
	}

	@Override
	public JsonParser configure(Feature f, boolean state) {
		wrappedParser = wrappedParser.configure(JacksonTransformers.transformFeature(f), state);
		return this;
	}

	@Override
	public JsonParser disable(Feature f) {
		wrappedParser = wrappedParser.disable(JacksonTransformers.transformFeature(f));
		return this;
	}

	@Override
	public JsonParser enable(Feature f) {
		wrappedParser = wrappedParser.enable(JacksonTransformers.transformFeature(f));
		return this;
	}

	@Override
	public <T> Iterator<T> readValuesAs(Class<T> valueType) throws IOException, JsonProcessingException {
		try {
			return wrappedParser.readValuesAs(valueType);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public <T> T readValueAs(Class<T> valueType) throws IOException, JsonProcessingException {
		try {
			return wrappedParser.readValueAs(valueType);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public boolean getValueAsBoolean(boolean defaultValue) throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsBoolean(defaultValue);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public JsonToken nextValue() throws IOException, JsonParseException {
		try {
			return JacksonTransformers.transformJsonToken(wrappedParser.nextValue());
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public boolean nextFieldName(SerializableString str) throws IOException, JsonParseException {
		try {
			return wrappedParser.nextFieldName(new SerializedString(str.getValue()));
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public String nextTextValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.nextTextValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public int nextIntValue(int defaultValue) throws IOException, JsonParseException {
		try {
			return wrappedParser.nextIntValue(defaultValue);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public long nextLongValue(long defaultValue) throws IOException, JsonParseException {
		try {
			return wrappedParser.nextLongValue(defaultValue);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public Boolean nextBooleanValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.nextBooleanValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public JsonToken getCurrentToken() {
		return JacksonTransformers.transformJsonToken(wrappedParser.getCurrentToken());
	}

	@Override
	public boolean hasCurrentToken() {
		return wrappedParser.hasCurrentToken();
	}

	@Override
	public JsonToken getLastClearedToken() {
		return JacksonTransformers.transformJsonToken(wrappedParser.getLastClearedToken());
	}

	@Override
	public boolean isExpectedStartArrayToken() {
		return wrappedParser.isExpectedStartArrayToken();
	}

	@Override
	public boolean hasTextCharacters() {
		return wrappedParser.hasTextCharacters();
	}

	@Override
	public byte getByteValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getByteValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public short getShortValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getShortValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public boolean getBooleanValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getBooleanValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public Object getEmbeddedObject() throws IOException, JsonParseException {
		try {
			return wrappedParser.getEmbeddedObject();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public byte[] getBinaryValue() throws IOException, JsonParseException {
		try {
			return wrappedParser.getBinaryValue();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public int getValueAsInt() throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsInt();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public int getValueAsInt(int defaultValue) throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsInt(defaultValue);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public long getValueAsLong() throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsLong();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public long getValueAsLong(long defaultValue) throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsLong(defaultValue);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public double getValueAsDouble() throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsDouble();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public double getValueAsDouble(double defaultValue) throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsDouble(defaultValue);
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public boolean getValueAsBoolean() throws IOException, JsonParseException {
		try {
			return wrappedParser.getValueAsBoolean();
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public <T> T readValueAs(TypeReference<?> valueTypeRef) throws IOException, JsonProcessingException {
		try {
			return wrappedParser.readValueAs(JacksonTransformers.transformTypeReference(valueTypeRef));
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}

	@Override
	public <T> Iterator<T> readValuesAs(TypeReference<?> valueTypeRef) throws IOException, JsonProcessingException {
		try {
			return wrappedParser.readValuesAs(JacksonTransformers.transformTypeReference(valueTypeRef));
		} catch (com.fasterxml.jackson.core.JsonParseException e) {
			throw wrapJsonParseException(e);
		}
	}
}
