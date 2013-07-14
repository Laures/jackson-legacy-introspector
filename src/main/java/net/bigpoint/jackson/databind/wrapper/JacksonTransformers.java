/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import com.fasterxml.jackson.core.JsonToken;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.Base64Variants;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.lang.reflect.Type;

/**
 * @author Alexander
 * 
 */
public abstract class JacksonTransformers {

	/**
	 * transforms bean property instances from jackson 1 to jackson 2
	 * 
	 * @param property
	 * @return
	 */
	public static com.fasterxml.jackson.databind.BeanProperty transformBeanProperty(BeanProperty property) {
		if (property == null) {
			return null;
		}
		return null;
	}

	/**
	 * transforms java jackson 1 java type instances to jackson 2 java type instances.
	 * 
	 * @param type
	 * @param context
	 * @return
	 */
	public static com.fasterxml.jackson.databind.JavaType transformJavaType(JavaType type, DeserializationContext context) {
		return transformJavaType(type, unwrapDeserializationConfig(context).getConfig().getTypeFactory());
	}

	public static com.fasterxml.jackson.databind.JavaType transformJavaType(JavaType type,
			com.fasterxml.jackson.databind.SerializerProvider provider) {
		return transformJavaType(type, provider.getConfig().getTypeFactory());
	}

	protected static com.fasterxml.jackson.databind.JavaType transformJavaType(JavaType type,
			com.fasterxml.jackson.databind.type.TypeFactory fac) {
		if (type.isArrayType()) {
			return fac.constructArrayType(type.getContentType().getRawClass());
		}
		if (type.isCollectionLikeType()) {
			return fac.constructCollectionLikeType(type.getRawClass(), type.getContentType().getRawClass());
		}
		if (type.isMapLikeType()) {
			return fac.constructMapLikeType(type.getRawClass(), type.getKeyType().getRawClass(), type.getContentType()
					.getRawClass());
		}
		return fac.uncheckedSimpleType(type.getRawClass());
	}

	/**
	 * unwraps wrapper instances, null values are preserved
	 * 
	 * @param context
	 * @return
	 */
	public static com.fasterxml.jackson.databind.DeserializationContext unwrapDeserializationConfig(
			DeserializationContext context) {
		if (context == null) {
			return null;
		}
		return ((DeserializationContext2to1Wrapper) context).unwrap();
	}

	/**
	 * unwraps wrapper instances, null values are preserved
	 */
	public static com.fasterxml.jackson.core.JsonParser unwrapParser(JsonParser parser) {
		if (parser == null) {
			return null;
		} else {
			return ((JsonParser2To1Wrapper) parser).unwrap();
		}
	}

	/**
	 * unwraps wrapper instances, null values are preserved
	 */
	public static com.fasterxml.jackson.core.JsonGenerator unwrapGenerator(JsonGenerator generator) {
		if (generator == null) {
			return null;
		} else {
			return ((JsonGenerator2To1Wrapper) generator).unwrap();
		}
	}

	public static JsonParseException wrapJsonParseException(com.fasterxml.jackson.core.JsonParseException exception) {
		return new JsonParseException("wrapped exception", new JsonLocation(exception.getLocation().getSourceRef(),
				exception.getLocation().getCharOffset(), exception.getLocation().getByteOffset(), exception.getLocation()
						.getLineNr(), exception.getLocation().getColumnNr()), exception);
	}

	public static JsonGenerationException wrapJsonGenerationException(
			com.fasterxml.jackson.core.JsonGenerationException exception) {
		return new JsonGenerationException(exception);
	}

	public static JsonProcessingException wrapProcessingException(com.fasterxml.jackson.core.JsonProcessingException e) {
		return new JsonParseException("wrapped exception", new JsonLocation(e.getLocation().getSourceRef(), e.getLocation()
				.getByteOffset(), e.getLocation().getCharOffset(), e.getLocation().getLineNr(), e.getLocation().getColumnNr()),
				e);
	}

	public static JsonMappingException wrapMappingException(com.fasterxml.jackson.databind.JsonMappingException e) {
		return new JsonMappingException("wrapped exception", new JsonLocation(e.getLocation().getSourceRef(), e
				.getLocation().getByteOffset(), e.getLocation().getCharOffset(), e.getLocation().getLineNr(), e.getLocation()
				.getColumnNr()), e);
	}

	public static com.fasterxml.jackson.core.Base64Variant transformBase64Variant(Base64Variant b64variant) {
		if (b64variant == null) {
			return null;
		}
		if (b64variant == Base64Variants.MIME) {
			return com.fasterxml.jackson.core.Base64Variants.MIME;
		}
		if (b64variant == Base64Variants.MIME_NO_LINEFEEDS) {
			return com.fasterxml.jackson.core.Base64Variants.MIME_NO_LINEFEEDS;
		}
		if (b64variant == Base64Variants.MODIFIED_FOR_URL) {
			return com.fasterxml.jackson.core.Base64Variants.MODIFIED_FOR_URL;
		}
		if (b64variant == Base64Variants.PEM) {
			return com.fasterxml.jackson.core.Base64Variants.PEM;
		}
		throw new UnsupportedOperationException("Only default Base64Variants are supported.");
	}

	public static org.codehaus.jackson.JsonToken transformJsonToken(JsonToken token) {
		if (token == null) {
			return null;
		}
		return org.codehaus.jackson.JsonToken.valueOf(token.name());
	}

	public static JsonParser.NumberType transformNumberType(com.fasterxml.jackson.core.JsonParser.NumberType numberType) {
		if (numberType == null) {
			return null;
		}
		return JsonParser.NumberType.valueOf(numberType.name());
	}

	public static com.fasterxml.jackson.core.type.TypeReference<?> transformTypeReference(final TypeReference<?> typeReference) {
		if (typeReference == null) {
			return null;
		}
		return new com.fasterxml.jackson.core.type.TypeReference<Object>() {
			@Override
			public Type getType() {
				return typeReference.getType();
			}
		};
	}

	public static com.fasterxml.jackson.core.JsonParser.Feature transformFeature(JsonParser.Feature feature) {
		if (feature == null) {
			return null;
		}
		return com.fasterxml.jackson.core.JsonParser.Feature.valueOf(feature.name());
	}
}
