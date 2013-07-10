package net.bigpoint.jackson.databind.introspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestArrayOrStringDeserializer {

	public static class ArrayOrStringDeserializer extends StdDeserializer<Set<String>> {

		public ArrayOrStringDeserializer() {
			super(Set.class);
		}

		@Override
		public JavaType getValueType() {
			return SimpleType.construct(String.class);
		}

		@Override
		public Set<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
			JsonToken token = jp.getCurrentToken();
			if (token.isScalarValue()) {
				String[] parts = jp.getText().split(",");
				return new LinkedHashSet<String>(Arrays.asList(parts));
			}
			return jp.readValueAs(new TypeReference<Set<String>>() {});
		}
	}

	private static class Bean {
		@JsonDeserialize(using = ArrayOrStringDeserializer.class)
		private Set<String> values;

		private Set<String> getValues() {
			return values;
		}
	}

	@Test
	public void testArrayOrStringDeserializer1() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(new JacksonLegacyIntrospector());

		String json = "{\"values\":[\"one\",\"two\"]}";
		Bean bean = mapper.readValue(json, Bean.class);

		assertEquals(new LinkedHashSet<String>(Arrays.asList("one", "two")), bean.getValues());
	}

	@Test
	public void testArrayOrStringDeserializer2() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(new JacksonLegacyIntrospector());

		String json = "{\"values\":\"one,two\"}";
		Bean bean = mapper.readValue(json, Bean.class);

		assertEquals(new LinkedHashSet<String>(Arrays.asList("one", "two")), bean.getValues());
	}
}
