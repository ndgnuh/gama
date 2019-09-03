/*********************************************************************************************
 *
 * 'GamlFile.java, in plugin ummisco.gama.gaml, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.file.gaml;

import org.eclipse.emf.common.util.URI;

import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.interfaces.IModel;
import msi.gama.common.interfaces.experiment.IExperimentPlan;
import msi.gama.common.interfaces.outputs.IOutput;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.file.GamaFile;
import msi.gama.util.list.GamaListFactory;
import msi.gama.util.list.IList;
import msi.gama.util.map.IMap;
import msi.gaml.compilation.GAML;
import msi.gaml.descriptions.ModelDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.operators.Cast;
import msi.gaml.types.IContainerType;
import msi.gaml.types.IType;
import msi.gaml.types.Types;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.example;
import ummisco.gama.processor.GamlAnnotations.file;

/**
 * Written by drogoul Modified on 13 nov. 2011
 *
 * @todo Description
 *
 */
@file (
		name = "gaml",
		extensions = { "gaml", "experiment" },
		buffer_type = IType.LIST,
		buffer_content = IType.SPECIES,
		buffer_index = IType.INT,
		concept = { IConcept.FILE },
		doc = @doc ("Represents GAML model files"))
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamlFile extends GamaFile<IList<IModel>, IModel> {

	private final IModel mymodel;
	/**
	 * @throws GamaRuntimeException
	 * @param scope
	 * @param pathName
	 */
	private final String experimentName;

	private final String aliasName;

	@doc (
			value = "This file constructor allows to read a gaml file (.gaml)",
			examples = { @example (
					value = "file f <- gaml_file(\"file.gaml\");",
					isExecutable = false) })
	public GamlFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
		experimentName = "";
		aliasName = "";
		mymodel = GAML.compile(URI.createURI(getPath(scope), false), null);
	}

	@Override
	public IContainerType getGamlType() {
		return Types.FILE.of(Types.INT, Types.SPECIES);
	}

	@doc (
			value = "This file constructor allows to compile a gaml file and run an experiment",
			examples = { @example (
					value = "file f <- gaml_file(\"file.gaml\", \"my_experiment\", \"my_model\");",
					isExecutable = false) })
	public GamlFile(final IScope scope, final String pathName, final String expName, final String cName)
			throws GamaRuntimeException {
		super(scope, pathName);
		experimentName = expName;
		aliasName = cName;
		mymodel = GAML.compile(URI.createURI(getPath(scope), false), null);
		((ModelDescription) mymodel.getDescription()).setAlias(aliasName);
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		return GamaListFactory.EMPTY_LIST;
	}

	public IExperimentPlan createExperiment(final String expName) {
		final IExperimentPlan exp = mymodel.getExperiment("Experiment " + expName);
		for (final IOutput o : exp.getOriginalSimulationOutputs()) {
			o.setName(o.getName() + "#" + aliasName);
		}
		for (final IOutput o : exp.getExperimentOutputs()) {
			o.setName(o.getName() + "#" + aliasName);
		}

		// GAMA.addGuiExperiment(exp);

		return exp;
	}

	public void execute(final IScope scope, final IExpression with_exp, final IExpression param_input,
			final IExpression param_output, final IExpression reset, final IExpression repeat,
			final IExpression stopCondition, final IExpression share) {
		final IExperimentPlan experiment = createExperiment(experimentName);

		if (param_input != null) {
			final IMap in = Cast.asMap(scope, param_input.value(scope), true);
			for (int i = 0; i < in.getKeys().size(); i++) {
				experiment.getModel().getVar(in.getKeys().get(i).toString()).setValue(null, in.getValues().get(i));
			}
		}
		GAMA.openExperimentFromGamlFile(experiment);
	}

	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		if (getBuffer() != null) { return; }
		setBuffer(GamaListFactory.<IModel> create(Types.SPECIES));
		((IList) getBuffer()).add(mymodel.getSpecies());
	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		return null;
	}

}
