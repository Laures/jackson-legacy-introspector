package net.bigpoint.jackson.databind.introspect;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import net.bigpoint.jackson.databind.BaseMapTest;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreType;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonTypeResolver;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.jsontype.impl.StdTypeResolverBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeFactory;

@SuppressWarnings("serial")
public class TestJacksonLegacyAnnotationIntrospector extends BaseMapTest {
	public static enum EnumExample {
		VALUE1;
	}

	public static class JacksonExample {
		private String attributeProperty;
		private String elementProperty;
		private List<String> wrappedElementProperty;
		private EnumExample enumProperty;
		private QName qname;

		@JsonSerialize(using = QNameSerializer.class)
		public QName getQname() {
			return qname;
		}

		@JsonDeserialize(using = QNameDeserializer.class)
		public void setQname(QName qname) {
			this.qname = qname;
		}

		@JsonProperty("myattribute")
		public String getAttributeProperty() {
			return attributeProperty;
		}

		@JsonProperty("myattribute")
		public void setAttributeProperty(String attributeProperty) {
			this.attributeProperty = attributeProperty;
		}

		@JsonProperty("myelement")
		public String getElementProperty() {
			return elementProperty;
		}

		@JsonProperty("myelement")
		public void setElementProperty(String elementProperty) {
			this.elementProperty = elementProperty;
		}

		@JsonProperty("mywrapped")
		public List<String> getWrappedElementProperty() {
			return wrappedElementProperty;
		}

		@JsonProperty("mywrapped")
		public void setWrappedElementProperty(List<String> wrappedElementProperty) {
			this.wrappedElementProperty = wrappedElementProperty;
		}

		public EnumExample getEnumProperty() {
			return enumProperty;
		}

		public void setEnumProperty(EnumExample enumProperty) {
			this.enumProperty = enumProperty;
		}
	}

	public static class QNameSerializer extends JsonSerializer<QName> {

		@Override
		public void serialize(QName value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
				JsonProcessingException {
			jgen.writeString(value.toString());
		}
	}

	public static class QNameDeserializer extends StdDeserializer<QName> {
		public QNameDeserializer() {
			super(QName.class);
		}

		@Override
		public QName deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return QName.valueOf(jp.readValueAs(String.class));
		}
	}

	public static class DummyBuilder extends StdTypeResolverBuilder
	// <DummyBuilder>
	{
	}

	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	@JsonTypeResolver(DummyBuilder.class)
	static class TypeResolverBean {
	}

	// @since 1.7
	@JsonIgnoreType
	static class IgnoredType {
	}

	static class IgnoredSubType extends IgnoredType {
	}
	
	static class SimpleIgnore {
		String data;

		@JsonIgnore
		public String getData() {
			return data;
		}

		@JsonIgnore
		public void setData(String data) {
			this.data = data;
		}		
	}

	// Test to ensure we can override enum settings
	static class LcEnumIntrospector extends JacksonLegacyIntrospector {
		private static final long serialVersionUID = 1L;

		@Override
		public String findEnumValue(Enum<?> value) {
			return value.name().toLowerCase();
		}
	}

	/*
	/**********************************************************
	/* Unit tests
	/**********************************************************
	 */

	/**
	 * tests getting serializer/deserializer instances.
	 */
	@Test
	public void testSerializeDeserialize() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(new JacksonLegacyIntrospector());
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		JacksonExample ex = new JacksonExample();
		QName qname = new QName("urn:hi", "hello");
		ex.setQname(qname);
		ex.setAttributeProperty("attributeValue");
		ex.setElementProperty("elementValue");
		ex.setWrappedElementProperty(Arrays.asList("wrappedElementValue"));
		ex.setEnumProperty(EnumExample.VALUE1);

		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, ex);
		writer.flush();
		writer.close();

		String json = writer.toString();

		System.out.println(json);

		JacksonExample readEx = mapper.readValue(json, JacksonExample.class);

		Assert.assertEquals(ex.qname, readEx.qname);
		Assert.assertEquals(ex.attributeProperty, readEx.attributeProperty);
		Assert.assertEquals(ex.elementProperty, readEx.elementProperty);
		Assert.assertEquals(ex.wrappedElementProperty, readEx.wrappedElementProperty);
		Assert.assertEquals(ex.enumProperty, readEx.enumProperty);
	}

	@Test
	@Ignore
	public void testJsonTypeResolver() throws Exception {
		JacksonLegacyIntrospector ai = new JacksonLegacyIntrospector();
		AnnotatedClass ac = AnnotatedClass.constructWithoutSuperTypes(TypeResolverBean.class, ai, null);
		JavaType baseType = TypeFactory.defaultInstance().constructType(TypeResolverBean.class);
		ObjectMapper mapper = new ObjectMapper();
		TypeResolverBuilder<?> rb = ai.findTypeResolver(mapper.getDeserializationConfig(), ac, baseType);
		Assert.assertNotNull(rb);
		Assert.assertSame(DummyBuilder.class, rb.getClass());
	}

	/**
	 * Tests to ensure that {@link JsonIgnoreType} is detected as expected by the standard introspector.
	 * 
	 * @since 1.7
	 */
	@Test
	public void testIgnoredType() throws Exception {
		JacksonLegacyIntrospector ai = new JacksonLegacyIntrospector();
		AnnotatedClass ac = AnnotatedClass.construct(IgnoredType.class, ai, null);
		Assert.assertEquals(Boolean.TRUE, ai.isIgnorableType(ac));

		// also, should inherit as expected
		ac = AnnotatedClass.construct(IgnoredSubType.class, ai, null);
		Assert.assertEquals(Boolean.TRUE, ai.isIgnorableType(ac));
	}
	
	@Test
	public void testSimpleIgnore() throws Exception {
		JacksonLegacyIntrospector ai = new JacksonLegacyIntrospector();

		AnnotatedClass ac = AnnotatedClass.construct(SimpleIgnore.class, ai, null);
		AnnotatedMember am = ac.memberMethods().iterator().next();
		Assert.assertTrue(ai.hasIgnoreMarker(am));
	}

	@Test
	public void testEnumHandling() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(new LcEnumIntrospector());
		Assert.assertEquals("\"value1\"", mapper.writeValueAsString(EnumExample.VALUE1));
		EnumExample result = mapper.readValue(quote("value1"), EnumExample.class);
		Assert.assertEquals(EnumExample.VALUE1, result);
	}
}