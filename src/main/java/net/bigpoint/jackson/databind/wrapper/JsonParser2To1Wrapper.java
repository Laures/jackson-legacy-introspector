/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;

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

	protected JsonParseException wrapJsonParseException(com.fasterxml.jackson.core.JsonParseException exception) {
		return new JsonParseException("wrapped exception", new JsonLocation(exception.getLocation().getSourceRef(),
				exception.getLocation().getCharOffset(), exception.getLocation().getByteOffset(), exception.getLocation()
						.getLineNr(), exception.getLocation().getColumnNr()), exception);
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
	public void close() throws IOException {
		wrappedParser.close();
	}

	@Override
	public JsonToken nextToken() throws IOException, JsonParseException {
		try {
			return JsonToken.valueOf(wrappedParser.nextToken().name());
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
	public JsonStreamContext getParsingContext() {
		// TODO Auto-generated method stub
		return null;
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
			return NumberType.valueOf(wrappedParser.getNumberType().name());
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
	public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCurrentToken() {
		wrappedParser.clearCurrentToken();
	}

	@Override
	public JsonParser configure(Feature f, boolean state) {
		wrappedParser = wrappedParser.configure(com.fasterxml.jackson.core.JsonParser.Feature.valueOf(f.name()), state);
		return this;
	}

	@Override
	public JsonParser disable(Feature f) {
		wrappedParser = wrappedParser.disable(com.fasterxml.jackson.core.JsonParser.Feature.valueOf(f.name()));
		return this;
	}

	@Override
	public JsonParser enable(Feature f) {
		wrappedParser = wrappedParser.enable(com.fasterxml.jackson.core.JsonParser.Feature.valueOf(f.name()));
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
}
