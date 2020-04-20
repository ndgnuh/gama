package gama.processor.engine;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface IProcessor<T extends Annotation> {

	default void process(final ProcessorContext context) {}

	void serialize(final ProcessorContext context, Collection<StringBuilder> elements, final StringBuilder sb);

	default String getInitializationMethodName() {
		return null;
	}

	default String getExceptions() {
		return "";
	}

	default boolean outputToJava() {
		return true;
	}

	boolean hasElements();

	void writeJavaBody(StringBuilder sb, ProcessorContext context);

}
