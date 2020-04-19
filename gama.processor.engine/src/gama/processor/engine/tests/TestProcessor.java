package gama.processor.engine.tests;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

import gama.processor.annotations.GamlAnnotations.action;
import gama.processor.annotations.GamlAnnotations.constant;
import gama.processor.annotations.GamlAnnotations.file;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.operator;
import gama.processor.annotations.GamlAnnotations.setter;
import gama.processor.annotations.GamlAnnotations.skill;
import gama.processor.annotations.GamlAnnotations.species;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.processor.annotations.GamlAnnotations.test;
import gama.processor.annotations.GamlAnnotations.tests;
import gama.processor.annotations.GamlAnnotations.type;
import gama.processor.engine.ElementProcessor;
import gama.processor.engine.ProcessorContext;

public class TestProcessor extends ElementProcessor<tests> {

	@Override
	public void serialize(final ProcessorContext context, final StringBuilder sb) {}

	@Override
	public void process(final ProcessorContext context) {
		// Processes tests annotations
		super.process(context);
		// Special case for lone test annotations
		final Map<String, List<Element>> elements = context.groupElements(test.class);
		for (final Map.Entry<String, List<Element>> entry : elements.entrySet()) {

			if (opIndex1.keySet().size() < 20) {
				final StringBuilder sb = opIndex1.getOrDefault(entry.getKey(), new StringBuilder());
				for (final Element e : entry.getValue()) {
					try {
						createElement(sb, context, e, createFrom(e.getAnnotation(test.class)));
					} catch (final Exception exception) {
						context.emitError("Exception in processor: " + exception.getMessage(), e);
					}

				}
				opIndex1.put(entry.getKey(), sb);
			} else {
				final StringBuilder sb = opIndex2.getOrDefault(entry.getKey(), new StringBuilder());
				for (final Element e : entry.getValue()) {
					try {
						createElement(sb, context, e, createFrom(e.getAnnotation(test.class)));
					} catch (final Exception exception) {
						context.emitError("Exception in processor: " + exception.getMessage(), e);
					}

				}
				opIndex2.put(entry.getKey(), sb);
			}
		}
	}

	@Override
	public boolean outputToJava() {
		return false;
	}

	private tests createFrom(final test test) {
		return new tests() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return tests.class;
			}

			@Override
			public test[] value() {
				return new test[] { test };
			}
		};
	}

	@Override
	public void createElement(final StringBuilder sb, final ProcessorContext context, final Element e,
			final tests tests) {
		final String name = getTestName(determineName(context, e, tests));
		sb.append(ln).append(tab).append("test ").append(name).append(" {");
		for (final test test : tests.value()) {
			final String[] lines = determineText(context, test).split(";");
			for (final String line : lines) {
				if (!line.isEmpty()) {
					sb.append(ln).append(tab).append(tab).append(line).append(';');
				}
			}
		}
		// Output the footer
		sb.append(ln).append(tab).append("}").append(ln);
	}

	public void writeTests(final ProcessorContext context, final Writer sb) throws IOException {
		sb.append("experiment ").append(toJavaString("Tests for " + context.currentPlugin)).append(" type: test {");

		if (opIndex1.keySet().size() < 20) {
			for (final StringBuilder tests : opIndex1.values()) {
				sb.append(ln);
				sb.append(tests);
			}
		} else {
			for (final StringBuilder tests : opIndex2.values()) {
				sb.append(ln);
				sb.append(tests);
			};
		}
		sb.append(ln).append('}');
		namesAlreadyUsed.clear();
	}

	private String determineText(final ProcessorContext context, final test test) {
		String text = test.value().trim();
		final int lastSemiColon = text.lastIndexOf(';');
		String lastAssert = text.substring(lastSemiColon + 1);
		text = text.substring(0, lastSemiColon + 1);
		if (lastAssert.isEmpty()) { return text; }
		if (test.warning()) {
			lastAssert += " warning: true";
		}
		return text + "assert " + lastAssert + ";";
	}

	private String determineName(final ProcessorContext context, final Element e, final tests tests) {
		String testName = null;
		// Looking for named tests and concatenating their individual names
		for (final test test : tests.value()) {
			final String individualName = test.name();
			if (!individualName.isEmpty()) {
				if (testName == null) {
					testName = individualName;
				} else {
					testName += " and " + individualName;
				}
			}
		}
		// No named tests, proceed by inferring the name from the GAML artefact (if present)
		if (testName == null) {
			for (final Annotation a : context.getUsefulAnnotationsOn(e)) {
				switch (a.annotationType().getSimpleName()) {
					case "operator":
						testName = "Operator " + ((operator) a).value()[0];
						break;
					case "constant":
						testName = "Constant " + ((constant) a).value();
						break;
					case "symbol":
						testName = ((symbol) a).name()[0];
						break;
					case "type":
						testName = "Type " + ((type) a).name();
						break;
					case "skill":
						testName = "Skill " + ((skill) a).name();
						break;
					case "species":
						testName = "Species " + ((species) a).name();
						break;
					case "file":
						testName = ((file) a).name() + " File";
						break;
					case "action":
						testName = "Action " + ((action) a).name();
						break;
					case "getter":
						testName = "Getting " + ((getter) a).value();
						break;
					case "setter":
						testName = "Setting " + ((setter) a).value();
				}
				if (testName != null) {
					break;
				}
			}
		}
		// No named tests and no GAML artefact present; grab the name of the Java element as a last call
		if (testName == null) {
			testName = e.getSimpleName().toString();
		}
		return testName;
	}

	@Override
	protected Class<tests> getAnnotationClass() {
		return tests.class;
	}

	final Map<String, Integer> namesAlreadyUsed = new HashMap<>();

	private String getTestName(final String name) {
		String result = name;
		if (namesAlreadyUsed.containsKey(name)) {
			final int number = namesAlreadyUsed.get(name) + 1;
			namesAlreadyUsed.put(name, number);
			result = name + " (" + number + ")";
		} else {
			namesAlreadyUsed.put(name, 0);
		}
		return toJavaString(result);
	}

}
