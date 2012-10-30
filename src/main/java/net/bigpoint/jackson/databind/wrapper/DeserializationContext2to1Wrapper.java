/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.util.ArrayBuilders;
import org.codehaus.jackson.map.util.ObjectBuffer;
import org.codehaus.jackson.type.JavaType;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

/**
 * @author abaetz
 * 
 */
public class DeserializationContext2to1Wrapper extends DeserializationContext {

	public static final String EXCEPTION_WRAPPER_MESSAGE = "wrapped exception";

	private com.fasterxml.jackson.databind.DeserializationContext wrappedContext;

	private ObjectBuffer _objectBuffer;

	private ArrayBuilders _arrayBuilders;

	/**
	 * @param config
	 */
	protected DeserializationContext2to1Wrapper(com.fasterxml.jackson.databind.DeserializationContext wrappedContext) {
		super(new DeserializationConfig(null, null, null, null, null, null, null));
		this.wrappedContext = wrappedContext;
	}

	/**
	 * @return the wrappedContext
	 */
	public com.fasterxml.jackson.databind.DeserializationContext unwrap() {
		return wrappedContext;
	}

	protected String _calcName(Class<?> cls) {
		if (cls.isArray()) {
			return _calcName(cls.getComponentType()) + "[]";
		}
		return cls.getName();
	}

	protected com.fasterxml.jackson.core.JsonParser unwrapParser(JsonParser parser) {
		if (parser == null) {
			return null;
		} else {
			return ((JsonParser2To1Wrapper) parser).unwrap();
		}
	}

	@Override
	public JsonParser getParser() {
		// TODO parser wrapper
		return null;
	}

	@Override
	public Object findInjectableValue(Object valueId, BeanProperty forProperty, Object beanInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final ObjectBuffer leaseObjectBuffer() {
		ObjectBuffer buf = _objectBuffer;
		if (buf == null) {
			buf = new ObjectBuffer();
		} else {
			_objectBuffer = null;
		}
		return buf;
	}

	@Override
	public final void returnObjectBuffer(ObjectBuffer buf) {
		/* Already have a reusable buffer? Let's retain bigger one
		 * (or if equal, favor newer one, shorter life-cycle)
		 */
		if (_objectBuffer == null || buf.initialCapacity() >= _objectBuffer.initialCapacity()) {
			_objectBuffer = buf;
		}
	}

	@Override
	public final ArrayBuilders getArrayBuilders() {
		if (_arrayBuilders == null) {
			_arrayBuilders = new ArrayBuilders();
		}
		return _arrayBuilders;
	}

	@Override
	public Date parseDate(String dateStr) throws IllegalArgumentException {
		return wrappedContext.parseDate(dateStr);
	}

	@Override
	public Calendar constructCalendar(Date d) {
		return wrappedContext.constructCalendar(d);
	}

	@Override
	public boolean handleUnknownProperty(JsonParser jp, JsonDeserializer<?> deser, Object instanceOrClass, String propName)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JsonMappingException mappingException(Class<?> targetClass) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, wrappedContext.mappingException(targetClass));
	}

	@Override
	public JsonMappingException mappingException(Class<?> targetClass, JsonToken t) {
		String clsName = _calcName(targetClass);
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE,
				com.fasterxml.jackson.databind.JsonMappingException.from(wrappedContext.getParser(),
						"Can not deserialize instance of " + clsName + " out of " + t + " token"));
	}

	@Override
	public JsonMappingException instantiationException(Class<?> instClass, Throwable t) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, wrappedContext.instantiationException(instClass, t));
	}

	@Override
	public JsonMappingException instantiationException(Class<?> instClass, String msg) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, wrappedContext.instantiationException(instClass, msg));
	}

	@Override
	public JsonMappingException weirdStringException(Class<?> instClass, String msg) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, wrappedContext.weirdStringException(instClass, msg));
	}

	@Override
	public JsonMappingException weirdNumberException(Class<?> instClass, String msg) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, wrappedContext.weirdNumberException(instClass, msg));
	}

	@Override
	public JsonMappingException weirdKeyException(Class<?> keyClass, String keyValue, String msg) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE,
				wrappedContext.weirdKeyException(keyClass, keyValue, msg));
	}

	/**
	 * INFO: the given parser will be ignored
	 */
	// TODO maybe unwrapp parser?
	@Override
	public JsonMappingException wrongTokenException(JsonParser jp, JsonToken expToken, String msg) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, UnrecognizedPropertyException.from(
				wrappedContext.getParser(), "Unexpected token , expected " + expToken + ": " + msg));
	}

	@Override
	public JsonMappingException unknownFieldException(Object instanceOrClass, String fieldName) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE, UnrecognizedPropertyException.from(
				wrappedContext.getParser(), instanceOrClass, fieldName, null));
	}

	@Override
	public JsonMappingException unknownTypeException(JavaType baseType, String id) {
		return new JsonMappingException(EXCEPTION_WRAPPER_MESSAGE,
				com.fasterxml.jackson.databind.JsonMappingException.from(wrappedContext.getParser(),
						"Could not resolve type id '" + id + "' into a subtype of " + baseType));
	}
}
