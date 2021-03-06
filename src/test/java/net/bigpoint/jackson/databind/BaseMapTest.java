package net.bigpoint.jackson.databind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.bigpoint.jackson.BaseTest;

import org.junit.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public abstract class BaseMapTest extends BaseTest {
	private final static Object SINGLETON_OBJECT = new Object();

	/*
	/**********************************************************
	/* Shared helper classes
	/**********************************************************
	 */

	/**
	 * Simple wrapper around boolean types, usually to test value conversions or wrapping
	 */
	protected static class BooleanWrapper {
		public Boolean b;

		@JsonCreator
		public BooleanWrapper(Boolean value) {
			b = value;
		}

		@JsonValue
		public Boolean value() {
			return b;
		}
	}

	protected static class IntWrapper {
		public int i;

		public IntWrapper() {
		}

		public IntWrapper(int value) {
			i = value;
		}
	}

	/**
	 * Simple wrapper around String type, usually to test value conversions or wrapping
	 */
	protected static class StringWrapper {
		public String str;

		public StringWrapper() {
		}

		public StringWrapper(String value) {
			str = value;
		}
	}

	protected static class ObjectWrapper {
		private final Object object;

		protected ObjectWrapper(final Object object) {
			this.object = object;
		}

		public Object getObject() {
			return object;
		}

		@JsonCreator
		static ObjectWrapper jsonValue(final Object object) {
			return new ObjectWrapper(object);
		}
	}

	protected static class ListWrapper<T> {
		public List<T> list;

		public ListWrapper(T... values) {
			list = new ArrayList<T>();
			for (T value : values) {
				list.add(value);
			}
		}
	}

	protected static class MapWrapper<K, V> {
		public Map<K, V> map;

		public MapWrapper(Map<K, V> m) {
			map = m;
		}
	}

	protected static class ArrayWrapper<T> {
		public T[] array;

		public ArrayWrapper(T[] v) {
			array = v;
		}
	}

	/**
	 * Enumeration type with sub-classes per value.
	 */
	protected enum EnumWithSubClass {
		A {
			@Override
			public void foobar() {
			}
		},
		B {
			@Override
			public void foobar() {
			}
		};

		public abstract void foobar();
	}

	protected BaseMapTest() {
		super();
	}

	/*
	/**********************************************************
	/* Additional assert methods
	/**********************************************************
	 */

	private final static ObjectMapper SHARED_MAPPER = new ObjectMapper();

	protected ObjectWriter objectWriter() {
		return SHARED_MAPPER.writer();
	}

	protected ObjectReader objectReader() {
		return SHARED_MAPPER.reader();
	}

	protected ObjectReader objectReader(Class<?> cls) {
		return SHARED_MAPPER.reader(cls);
	}

	/*
	/**********************************************************
	/* Additional assert methods
	/**********************************************************
	 */

	protected void assertEquals(int[] exp, int[] act) {
		Assert.assertArrayEquals(exp, act);
	}

	/**
	 * Helper method for verifying 3 basic cookie cutter cases; identity comparison (true), and against null (false), or
	 * object of different type (false)
	 */
	protected void assertStandardEquals(Object o) {
		Assert.assertTrue(o.equals(o));
		Assert.assertFalse(o.equals(null));
		Assert.assertFalse(o.equals(SINGLETON_OBJECT));
		// just for fun, let's also call hash code...
		o.hashCode();
	}

	/*
	/**********************************************************
	/* Helper methods
	/**********************************************************
	 */

	@SuppressWarnings("unchecked")
	protected Map<String, Object> writeAndMap(ObjectMapper m, Object value) throws IOException {
		String str = m.writeValueAsString(value);
		return m.readValue(str, Map.class);
	}

	protected <T> T readAndMapFromString(ObjectMapper m, String input, Class<T> cls) throws IOException {
		return m.readValue("\"" + input + "\"", cls);
	}

	protected String serializeAsString(ObjectMapper m, Object value) throws IOException {
		return m.writeValueAsString(value);
	}

	protected String serializeAsString(Object value) throws IOException {
		return serializeAsString(new ObjectMapper(), value);
	}

	protected String asJSONObjectValueString(Object... args) throws IOException {
		return asJSONObjectValueString(new ObjectMapper(), args);
	}

	protected String asJSONObjectValueString(ObjectMapper m, Object... args) throws IOException {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
		for (int i = 0, len = args.length; i < len; i += 2) {
			map.put(args[i], args[i + 1]);
		}
		return m.writeValueAsString(map);
	}

	protected TimeZone getUTCTimeZone() {
		return TimeZone.getTimeZone("GMT");
	}

	protected byte[] utf8Bytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}