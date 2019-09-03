package gama.extensions.serialize.gamaType.reference;

import java.util.ArrayList;

import gama.extensions.serialize.gamaType.reduced.GamaPathReducer;
import gama.common.interfaces.IReference;
import gama.kernel.simulation.SimulationAgent;
import gama.util.path.GamaPath;

public class ReferencePath extends GamaPath implements IReference {

	ArrayList<AgentAttribute> agtAttr;
	
	GamaPathReducer pathReducer;

	public ReferencePath(GamaPathReducer p) {
		super();
		agtAttr = new ArrayList<AgentAttribute>();		
		pathReducer = p;
	}	

	@Override
	public Object constructReferencedObject(SimulationAgent sim) {
		pathReducer.unreferenceReducer(sim);
		return pathReducer.constructObject(sim.getScope());
	}	
	
	@Override
	public ArrayList<AgentAttribute> getAgentAttributes() {
		return agtAttr;
	}	

    public boolean equals(Object o) {
        if (o == this)
            return true;
        else
        	return false;
    }
}
