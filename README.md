# Jackson 1 to Jackson 2 Legacy Introspector [![Build Status](https://travis-ci.org/Laures/jackson-legacy-introspector.png)](https://travis-ci.org/Laures/jackson-legacy-introspector)
This project provides support for Jackson 1 annotations in Jackson 2

With the newest major version of the Jackson-JSON Processor the package names of all Jackson classes changed from `org.codehouse` to `com.fasterxml`. With this change all classes that were annotated for Jackson 1.9 no longer work with Jackson 2.

This project provides an `AnnotationIntrospector` that evaluates Jackson 1 Annotations for usage with Jackson 2 so such classes can be used in current projects.

# Usage

To use the `JacksonLegacyAnnotationIntrospector` simply configure it for you `ObjectMapper`

```
ObjectMapper mapper = new ObjectMapper();
mapper.setAnnotationIntrospector(new JacksonLegacyIntrospector());
```

# Not Supported Options

Because EVERY class in Jackson changed Custom (De-)Serializers are not supported yet. Every annotated (De-)Serializer is silently ignored!