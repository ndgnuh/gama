/*******************************************************************************************************
 *
 * gama.core.outputs.layers.AbstractLayerStatement.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.outputs.layers;

import com.google.common.primitives.Ints;

import gama.core.outputs.LayeredDisplayOutput;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IDisplayData;
import gama.common.interfaces.outputs.IDisplayOutput;
import gama.common.interfaces.outputs.ILayerStatement;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.Symbol;
import gaml.compilation.interfaces.ISymbol;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;

/**
 * Written by drogoul Modified on 9 nov. 2009
 *
 * GAML statement to define the properties of a layer in a display
 *
 * @todo Description
 *
 */
@inside (
		symbols = IKeyword.DISPLAY)
public abstract class AbstractLayerStatement extends Symbol implements ILayerStatement {

	LayeredDisplayOutput output;
	protected boolean layerToCreate = true;

	public boolean isToCreate() {
		return layerToCreate;
	}

	public AbstractLayerStatement(final IDescription desc) throws GamaRuntimeException {
		super(desc);
		setName(desc.getName());
	}

	@Override
	public IExpression getRefreshFacet() {
		return getFacet(IKeyword.REFRESH);
	}

	@Override
	public int compareTo(final ILayerStatement o) {
		return Ints.compare(getOrder(), o.getOrder());
	}

	@Override
	public final boolean init(final IScope scope) {
		return _init(scope);
	}

	protected abstract boolean _init(IScope scope);

	@Override
	public void setDisplayOutput(final IDisplayOutput out) {
		output = (LayeredDisplayOutput) out;
	}

	public LayeredDisplayOutput getDisplayOutput() {
		return output;
	}

	public IDisplayData getLayeredDisplayData() {
		if (output == null) { return null; }
		return output.getData();
	}

	@Override
	public final boolean step(final IScope scope) throws GamaRuntimeException {
		if (!scope.interrupted()) { return _step(scope); }
		return false;
	}

	protected abstract boolean _step(IScope scope);

	@Override
	public void setChildren(final Iterable<? extends ISymbol> children) {}

}
