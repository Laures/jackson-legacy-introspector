# Jackson 1 to Jackson 2 Legacy Introspector [![Build Status](https://laures.ci.cloudbees.com/buildStatus/icon?job=JacksonLegacyIntrospector)](https://laures.ci.cloudbees.com/job/JacksonLegacyIntrospector/) [![Dependency Status](https://www.versioneye.com/user/projects/525c17b3632bac09b70004f0/badge.png)](https://www.versioneye.com/user/projects/525c17b3632bac09b70004f0)
This project provides support for Jackson 1 annotations in Jackson 2

With the newest major version of the Jackson-JSON Processor the package names of all Jackson classes changed from `org.codehouse` to `com.fasterxml`. With this change all classes that were annotated for Jackson 1.9 no longer work with Jackson 2.

This project provides an `AnnotationIntrospector` that evaluates Jackson 1 Annotations for usage with Jackson 2 so such classes can be used in current projects.

# Usage

To use the `JacksonLegacyAnnotationIntrospector` simply configure it for you `ObjectMapper`

```
ObjectMapper mapper = new ObjectMapper();
mapper.setAnnotationIntrospector(new JacksonLegacyIntrospector());
```

(Optional) To use both Jackson 1 and 2 annotaions, register custome module by simple code

```
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new Module() {
  		@Override
			public Version version() {
				return new Version(1, 0, 0, "", "net.bigpoint.jackson", "JacksonLegacySupport");
			}
			
			@Override
			public void setupModule(SetupContext context) {
				context.appendAnnotationIntrospector(new JacksonLegacyIntrospector());
			}
			
			@Override
			public String getModuleName() {
				return "JacksonLegacySupport";
			}
		});
```

# Limitations

While the Introspector will wrap annotated custom Deserializer,Serializer, KeySerializer and the like so they can be used by Jackson 2 those wrappers do not provide the full featureset of Jackson 1. 

**Rule of thumb:** if the custom implementation makes a call that requires a parameter or has a return type that is not an Enum value or Basic Java Class, check the necessary wrapper if this method is provided.


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/Laures/jackson-legacy-introspector/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

