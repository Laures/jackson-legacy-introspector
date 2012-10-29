/**
 * 
 */
package net.bigpoint.jackson.databind.wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author abaetz
 * 
 */
public class AnnotationWrappingProxy<L extends Annotation> implements InvocationHandler {

	@SuppressWarnings("unchecked")
	public static <A extends Annotation, B extends Annotation> A of(Class<A> annotation, B instance) {
		return (A) Proxy.newProxyInstance(annotation.getClassLoader(), new Class[] { annotation },
				new AnnotationWrappingProxy<B>(instance));
	}

	private L annotation;

	private AnnotationWrappingProxy(L legacyAnnotation) {
		annotation = legacyAnnotation;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return annotation.annotationType().getMethod(method.getName()).invoke(annotation);
	}
}
