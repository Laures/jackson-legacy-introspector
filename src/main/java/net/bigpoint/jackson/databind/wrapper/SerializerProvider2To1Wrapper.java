/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import static net.bigpoint.jackson.databind.wrapper.JacksonTransformers.transformBeanProperty;
import static net.bigpoint.jackson.databind.wrapper.JacksonTransformers.transformJavaType;
import static net.bigpoint.jackson.databind.wrapper.JacksonTransformers.unwrapGenerator;
import static net.bigpoint.jackson.databind.wrapper.JacksonTransformers.wrapMappingException;
import static net.bigpoint.jackson.databind.wrapper.JacksonTransformers.wrapProcessingException;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerFactory;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.schema.JsonSchema;
import org.codehaus.jackson.type.JavaType;

/**
 * This class wrapps a jackson 2 serialization provider so it can be used by jackson 1 classes.
 * 
 * It expects all jackson 1 specific parameters to be wrapper instances that can be unwrapped into jackson 2 specific
 * instances.
 * 
 * @author abaetz
 * 
 */
public class SerializerProvider2To1Wrapper extends SerializerProvider {

	private com.fasterxml.jackson.databind.SerializerProvider wrappedProvider;

	/**
	 * @param config
	 */
	protected SerializerProvider2To1Wrapper(com.fasterxml.jackson.databind.SerializerProvider wrappedProvider) {
		super(null);
		this.wrappedProvider = wrappedProvider;
	}

	/**
	 * @return the wrappedProvider
	 */
	public com.fasterxml.jackson.databind.SerializerProvider unwrap() {
		return wrappedProvider;
	}

	@Override
	public JsonSerializer<Object> findValueSerializer(Class<?> runtimeType, BeanProperty property)
			throws JsonMappingException {
		try {
			return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.findValueSerializer(runtimeType,
					transformBeanProperty(property)));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapMappingException(e);
		}
	}

	@Override
	public JsonSerializer<Object> findValueSerializer(JavaType serializationType, BeanProperty property)
			throws JsonMappingException {
		try {
			return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.findValueSerializer(
					transformJavaType(serializationType, wrappedProvider), transformBeanProperty(property)));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapMappingException(e);
		}
	}

	@Override
	public JsonSerializer<Object> findTypedValueSerializer(Class<?> valueType, boolean cache, BeanProperty property)
			throws JsonMappingException {
		try {
			return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.findTypedValueSerializer(valueType, cache,
					transformBeanProperty(property)));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapMappingException(e);
		}
	}

	@Override
	public JsonSerializer<Object> findTypedValueSerializer(JavaType valueType, boolean cache, BeanProperty property)
			throws JsonMappingException {
		try {
			return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.findTypedValueSerializer(
					transformJavaType(valueType, wrappedProvider), cache, transformBeanProperty(property)));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapMappingException(e);
		}
	}

	@Override
	public JsonSerializer<Object> findKeySerializer(JavaType keyType, BeanProperty property) throws JsonMappingException {
		try {
			return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.findKeySerializer(
					transformJavaType(keyType, wrappedProvider), transformBeanProperty(property)));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapMappingException(e);
		}
	}

	@Override
	public JsonSerializer<Object> getNullKeySerializer() {
		return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.getDefaultNullKeySerializer());
	}

	@Override
	public JsonSerializer<Object> getNullValueSerializer() {
		return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.getDefaultNullValueSerializer());
	}

	@Override
	public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> unknownType) {
		return new JsonSerializer2To1Wrapper<Object>(wrappedProvider.getUnknownTypeSerializer(unknownType));
	}

	@Override
	public void defaultSerializeDateValue(long timestamp, JsonGenerator jgen) throws IOException, JsonProcessingException {
		try {
			wrappedProvider.defaultSerializeDateValue(timestamp, unwrapGenerator(jgen));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapProcessingException(e);
		}

	}

	@Override
	public void defaultSerializeDateValue(Date date, JsonGenerator jgen) throws IOException, JsonProcessingException {
		try {
			wrappedProvider.defaultSerializeDateValue(date, unwrapGenerator(jgen));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapProcessingException(e);
		}

	}

	@Override
	public void defaultSerializeDateKey(long timestamp, JsonGenerator jgen) throws IOException, JsonProcessingException {
		try {
			wrappedProvider.defaultSerializeDateKey(timestamp, unwrapGenerator(jgen));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapProcessingException(e);
		}

	}

	@Override
	public void defaultSerializeDateKey(Date date, JsonGenerator jgen) throws IOException, JsonProcessingException {
		try {
			wrappedProvider.defaultSerializeDateKey(date, unwrapGenerator(jgen));
		} catch (com.fasterxml.jackson.databind.JsonMappingException e) {
			throw wrapProcessingException(e);
		}

	}

	/*
	 * Will probably not be implemented
	 */

	@Override
	public int cachedSerializersCount() {
		// TODO impossible
		return 0;
	}

	@Override
	public void flushCachedSerializers() {
		// TODO impossible
	}

	@Override
	public void setNullKeySerializer(JsonSerializer<Object> nks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNullValueSerializer(JsonSerializer<Object> nvs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultKeySerializer(JsonSerializer<Object> ks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serializeValue(SerializationConfig cfg, JsonGenerator jgen, Object value, SerializerFactory jsf)
			throws IOException, JsonGenerationException {

	}

	@Override
	public void serializeValue(SerializationConfig cfg, JsonGenerator jgen, Object value, JavaType rootType,
			SerializerFactory jsf) throws IOException, JsonGenerationException {
		// TODO Auto-generated method stub

	}

	@Override
	public JsonSchema generateJsonSchema(Class<?> type, SerializationConfig config, SerializerFactory jsf)
			throws JsonMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasSerializerFor(SerializationConfig cfg, Class<?> cls, SerializerFactory jsf) {
		// TODO Auto-generated method stub
		return false;
	}

}
