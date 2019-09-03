package gama.processor.engine;

import javax.lang.model.element.Element;

import gama.processor.annotations.GamlAnnotations.experiment;

public class ExperimentProcessor extends ElementProcessor<experiment> {

	@Override
	protected Class<experiment> getAnnotationClass() {
		return experiment.class;
	}

	@Override
	public void createElement(final StringBuilder sb, final ProcessorContext context, final Element e,
			final experiment exp) {
		verifyDoc(context, e, "experiment " + exp.value(), exp);
		sb.append(in).append("_experiment(").append(toJavaString(exp.value())).append(",(p, i)->new ")
				.append(rawNameOf(context, e.asType())).append("(p, i));");
	}

}
