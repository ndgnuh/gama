package gama.extensions.serialize.gamaType.reference;

import java.util.ArrayList;

import gama.extensions.serialize.gamaType.reduced.GamaMapReducer;
import msi.gama.common.interfaces.IReference;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.util.map.GamaMap;

public class ReferenceMap extends GamaMap implements IReference {

	ArrayList<AgentAttribute> agtAttr;

	GamaMapReducer mapReducer;

	public ReferenceMap(final GamaMapReducer m) {
		super(m.getValues().size(), m.getKeysType(), m.getDataType());
		agtAttr = new ArrayList<>();
		mapReducer = m;
	}

	@Override
	public Object constructReferencedObject(final SimulationAgent sim) {
		mapReducer.unreferenceReducer(sim);
		return mapReducer.constructObject(sim.getScope());
	}

	@Override
	public ArrayList<AgentAttribute> getAgentAttributes() {
		return agtAttr;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else {
			return false;
		}
	}
}
