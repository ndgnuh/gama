/*********************************************************************************************
 *
 * 'PopulationEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import gama.core.outputs.ValuedDisplayOutputFactory;
import gama.ui.base.interfaces.EditorListener;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.common.interfaces.experiment.IParameter;
import gama.metamodel.population.IPopulation;
import gama.runtime.scope.IScope;
import gaml.species.ISpecies;

@SuppressWarnings ({ "rawtypes", "unchecked" })
public class PopulationEditor extends AbstractEditor<IContainer> {

	Text populationDisplayer;

	PopulationEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener l) {
		super(scope, agent, param, l);
	}

	@Override
	public Control createCustomParameterControl(final Composite compo) {
		populationDisplayer = new Text(compo, SWT.READ_ONLY);
		populationDisplayer.setEnabled(false);
		final GridData data = new GridData(GridData.FILL, GridData.CENTER, true, false);
		populationDisplayer.setLayoutData(data);
		return populationDisplayer;
	}

	@Override
	protected void displayParameterValue() {
		internalModification = true;
		final String s = currentValue instanceof IPopulation ? ((IPopulation) currentValue).getName()
				: currentValue == null ? "nil" : currentValue instanceof ISpecies
						? currentValue.getGamlType().toString() : currentValue.serialize(true);
		populationDisplayer.setText(s);
		populationDisplayer.setToolTipText(s);
		internalModification = false;
	}

	@Override
	public Control getEditorControl() {
		return populationDisplayer;
	}

	@Override
	protected void applyBrowse() {
		if (currentValue instanceof Collection) {
			ValuedDisplayOutputFactory.browse((Collection) currentValue);
		}
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { BROWSE };
	}

}
