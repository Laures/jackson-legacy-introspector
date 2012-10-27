/**
 * 
 */
package net.bigpoint.jackson.databind;

import static net.bigpoint.jackson.databind.AnnotationWrappingProxy.of;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnoreType;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonView;
import org.codehaus.jackson.map.annotate.NoClass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

/**
 * @author Alexander
 * 
 */
// TODO for all serializers: return instance of wrapped serializer as the class will implement the wrong interface!
public class JacksonLegacyIntrospector extends NopAnnotationIntrospector {

	private static final long serialVersionUID = 1L;

	@Override
	public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
		JsonAutoDetect ann = ac.getAnnotation(JsonAutoDetect.class);
		return (ann == null) ? checker : checker.with(of(com.fasterxml.jackson.annotation.JsonAutoDetect.class, ann));
	}

	@Override
	public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
		JsonSerialize ann = am.getAnnotation(JsonSerialize.class);
		if (ann != null) {
			Class<?> cls = ann.contentAs();
			if (cls != NoClass.class) {
				return cls;
			}
		}
		return null;
	}

	@Override
	public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
		// Primary annotation, JsonDeserialize
		JsonDeserialize ann = am.getAnnotation(JsonDeserialize.class);
		if (ann != null) {
			Class<?> cls = ann.keyAs();
			if (cls != NoClass.class) {
				return cls;
			}
		}
		return null;
	}

	@Override
	public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
		// Primary annotation, JsonDeserialize
		JsonDeserialize ann = am.getAnnotation(JsonDeserialize.class);
		if (ann != null) {
			Class<?> cls = ann.as();
			if (cls != NoClass.class) {
				return cls;
			}
		}
		return null;
	}

	@Override
	public String findEnumValue(Enum<?> value) {
		return value.name();
	}

	@Override
	public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
		JsonIgnoreProperties ignore = ac.getAnnotation(JsonIgnoreProperties.class);
		return (ignore == null) ? null : ignore.ignoreUnknown();
	}

	@Override
	public PropertyName findNameForDeserialization(Annotated a) {
		// [Issue#69], need bit of delegation
		// !!! TODO: in 2.2, remove old methods?
		String name;
		if (a instanceof AnnotatedField) {
			name = findDeserializationName((AnnotatedField) a);
		} else if (a instanceof AnnotatedMethod) {
			name = findDeserializationName((AnnotatedMethod) a);
		} else if (a instanceof AnnotatedParameter) {
			name = findDeserializationName((AnnotatedParameter) a);
		} else {
			name = null;
		}
		if (name != null) {
			if (name.length() == 0) { // empty String means 'default'
				return PropertyName.USE_DEFAULT;
			}
			return new PropertyName(name);
		}
		return null;
	}

	@Override
	public String findDeserializationName(AnnotatedMethod am) {
		// @JsonSetter has precedence over @JsonProperty, being more specific
		JsonSetter ann = am.getAnnotation(JsonSetter.class);
		if (ann != null) {
			return ann.value();
		}
		JsonProperty pann = am.getAnnotation(JsonProperty.class);
		if (pann != null) {
			return pann.value();
		}
		// @JsonSerialize implies that there is a property, but no name
		// 09-Apr-2010, tatu: Ditto for JsonView
		// 19-Oct-2011, tatu: And JsonBackReference/JsonManagedReference
		if (am.hasAnnotation(JsonDeserialize.class) || am.hasAnnotation(JsonView.class)
				|| am.hasAnnotation(JsonBackReference.class) || am.hasAnnotation(JsonManagedReference.class)) {
			return "";
		}
		return null;
	}

	@Override
	public String findDeserializationName(AnnotatedField af) {
		JsonProperty pann = af.getAnnotation(JsonProperty.class);
		if (pann != null) {
			return pann.value();
		}
		// Also: having JsonDeserialize implies it is such a property
		// 09-Apr-2010, tatu: Ditto for JsonView
		if (af.hasAnnotation(JsonDeserialize.class) || af.hasAnnotation(JsonView.class)
				|| af.hasAnnotation(JsonBackReference.class) || af.hasAnnotation(JsonManagedReference.class)) {
			return "";
		}
		return null;
	}

	@Override
	public String findDeserializationName(AnnotatedParameter param) {
		if (param != null) {
			JsonProperty pann = param.getAnnotation(JsonProperty.class);
			if (pann != null) {
				return pann.value();
			}
			/* And can not use JsonDeserialize as we can not use
			 * name auto-detection (names of local variables including
			 * parameters are not necessarily preserved in bytecode)
			 */
		}
		return null;
	}

	@Override
	public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
		/* No dedicated disabling; regular @JsonIgnore used
		 * if needs to be ignored (and if so, is handled prior
		 * to this method getting called)
		 */
		return am.hasAnnotation(JsonAnySetter.class);
	}

	@Override
	public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
		/* No dedicated disabling; regular @JsonIgnore used
		 * if needs to be ignored (handled separately
		 */
		return am.hasAnnotation(JsonAnyGetter.class);
	}

	@Override
	public boolean hasCreatorAnnotation(Annotated a) {
		/* No dedicated disabling; regular @JsonIgnore used
		 * if needs to be ignored (and if so, is handled prior
		 * to this method getting called)
		 */
		return a.hasAnnotation(JsonCreator.class);
	}

	/*
	/**********************************************************
	/* Serialization: property annotations
	/**********************************************************
	 */

	@Override
	public PropertyName findNameForSerialization(Annotated a) {
		// [Issue#69], need bit of delegation
		// !!! TODO: in 2.2, remove old methods?
		String name;
		if (a instanceof AnnotatedField) {
			name = findSerializationName((AnnotatedField) a);
		} else if (a instanceof AnnotatedMethod) {
			name = findSerializationName((AnnotatedMethod) a);
		} else {
			name = null;
		}
		if (name != null) {
			if (name.length() == 0) { // empty String means 'default'
				return PropertyName.USE_DEFAULT;
			}
			return new PropertyName(name);
		}
		return null;
	}

	@Override
	public String findSerializationName(AnnotatedField af) {
		JsonProperty pann = af.getAnnotation(JsonProperty.class);
		if (pann != null) {
			return pann.value();
		}
		// Also: having JsonSerialize implies it is such a property
		// 09-Apr-2010, tatu: Ditto for JsonView
		if (af.hasAnnotation(JsonSerialize.class) || af.hasAnnotation(JsonView.class)) {
			return "";
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String findSerializationName(AnnotatedMethod am) {
		// @JsonGetter is most specific, has precedence
		JsonGetter ann = am.getAnnotation(JsonGetter.class);
		if (ann != null) {
			return ann.value();
		}
		JsonProperty pann = am.getAnnotation(JsonProperty.class);
		if (pann != null) {
			return pann.value();
		}
		/* 22-May-2009, tatu: And finally, JsonSerialize implies
		 *   that there is a property, although doesn't define name
		 */
		// 09-Apr-2010, tatu: Ditto for JsonView
		if (am.hasAnnotation(JsonSerialize.class) || am.hasAnnotation(JsonView.class)) {
			return "";
		}
		return null;
	}

	@Override
	public boolean hasAsValueAnnotation(AnnotatedMethod am) {
		JsonValue ann = am.getAnnotation(JsonValue.class);
		// value of 'false' means disabled...
		return (ann != null && ann.value());
	}

	@Override
	public String[] findPropertiesToIgnore(Annotated ac) {
		JsonIgnoreProperties ignore = ac.getAnnotation(JsonIgnoreProperties.class);
		return (ignore == null) ? null : ignore.value();
	}

	@Override
	public Boolean isIgnorableType(AnnotatedClass ac) {
		JsonIgnoreType ignore = ac.getAnnotation(JsonIgnoreType.class);
		return (ignore == null) ? null : ignore.value();
	}

	/**
	 * Not possible with jackson 1.9
	 */
	@Override
	public Boolean hasRequiredMarker(AnnotatedMember m) {
		return super.hasRequiredMarker(m);
	}

	@Override
	public boolean hasIgnoreMarker(AnnotatedMember m) {
		JsonIgnore ann = m.getAnnotation(JsonIgnore.class);
		return (ann != null && ann.value());
	}

	/**
	 * Not possible with jackson 1.9
	 */
	@Override
	public Object findNamingStrategy(AnnotatedClass ac) {
		return super.findNamingStrategy(ac);
	}
}
