/*********************************************************************************************
 *
 * 'GamaPairReducer.java, in plugin ummisco.gama.serialize, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.extensions.serialize.gamaType.reduced;

import gama.extensions.serialize.gamaType.reference.ReferencePair;
import gama.common.interfaces.IReference;
import gama.kernel.simulation.SimulationAgent;
import gama.util.GamaPair;
import gaml.types.IType;

@SuppressWarnings ({ "rawtypes", "unchecked" })
public class GamaPairReducer {
	private final IType keyPairType;
	private final IType dataPairType;
	private Object key;
	private Object value;

	public GamaPairReducer(final GamaPair m) {
		keyPairType = m.getGamlType().getKeyType();
		dataPairType = m.getGamlType().getContentType();
		key = m.getKey();
		value = m.getValue();
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public IType getKeyType() {
		return keyPairType;
	}

	public IType getValueType() {
		return dataPairType;
	}

	public void setKey(final Object k) {
		key = k;
	}

	public void setValue(final Object v) {
		value = v;
	}

	public GamaPair constructObject() {
		final boolean isReference = IReference.isReference(key) || IReference.isReference(value);

		return isReference ? new ReferencePair(this) : new GamaPair(key, value, keyPairType, dataPairType);

		// return new GamaPair(key, value, keyPairType, dataPairType);
	}

	public void unreferenceReducer(final SimulationAgent sim) {
		key = IReference.getObjectWithoutReference(key, sim);
		value = IReference.getObjectWithoutReference(value, sim);
	}
}
