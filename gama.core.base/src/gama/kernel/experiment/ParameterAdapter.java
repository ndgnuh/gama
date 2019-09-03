/*******************************************************************************************************
 *
 * gama.kernel.experiment.ParameterAdapter.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.kernel.experiment;

import java.util.List;
import java.util.Set;

import gama.common.interfaces.experiment.IParameter;
import gama.common.util.StringUtils;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gaml.descriptions.SymbolDescription;
import gaml.types.IType;
import gaml.types.Types;

@SuppressWarnings ({ "rawtypes" })
public abstract class ParameterAdapter implements IParameter.Batch {

	private final int order = SymbolDescription.ORDER++;

	protected String title;
	protected final IType type;
	protected String category;
	protected String unitLabel;

	public ParameterAdapter(final String title, final int type) {
		this.title = title;
		this.type = Types.get(type);
	}

	public ParameterAdapter(final String title, final String category, final int type) {
		this(title, type);
		this.category = category;
	}

	public ParameterAdapter(final String title, final String category, final String unit, final int type) {
		this(title, category, type);
		this.unitLabel = unit;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getName() {
		return getTitle();
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public boolean canBeExplored() {
		return false;
	}

	@Override
	public void setEditable(final boolean editable) {}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getUnitLabel(final IScope scope) {
		return unitLabel;
	}

	@Override
	public void setValue(final IScope scope, final Object value) {}

	@Override
	public Object value(final IScope iScope) throws GamaRuntimeException {
		return value();
	}

	@Override
	public IType getType() {
		return type;
	}

	// @Override
	// public IType getContentType() {
	// return Types.NO_TYPE;
	// }

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return StringUtils.toGaml(value(), includingBuiltIn);
	}

	@Override
	public Object getInitialValue(final IScope scope) {
		return value(scope);
	}

	@Override
	public Number getMinValue(final IScope scope) {
		return null;
	}

	@Override
	public Number getMaxValue(final IScope scope) {
		return null;
	}

	@Override
	public List getAmongValue(final IScope scope) {
		return null;
	}

	@Override
	public void reinitRandomly(final IScope scope) {}

	//
	// @Override
	// public void tryToInit(IScope scope) {}

	@Override
	public Set<Object> neighborValues(final IScope scope) throws GamaRuntimeException {
		return null;
	}

	@Override
	public Number getStepValue(final IScope scope) {
		return null;
	}

	@Override
	public void setCategory(final String name) {
		category = name;
	}

	/**
	 * Method setUnitLabel()
	 *
	 * @see gama.common.interfaces.experiment.IParameter#setUnitLabel(java.lang.String)
	 */
	@Override
	public void setUnitLabel(final String label) {
		unitLabel = label;
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void setDefined(final boolean defined) {}

	@Override
	public boolean acceptsSlider(final IScope scope) {
		return false;
	}

	@Override
	public List<GamaColor> getColor(final IScope scope) {
		return null;
	}

}
