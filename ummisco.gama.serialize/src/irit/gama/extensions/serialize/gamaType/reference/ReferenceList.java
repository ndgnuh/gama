package irit.gama.extensions.serialize.gamaType.reference;

import java.util.ArrayList;

import irit.gama.extensions.serialize.gamaType.reduced.GamaListReducer;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.util.GamaList;
import msi.gama.util.IReference;

public class ReferenceList extends GamaList implements IReference {

	ArrayList<AgentAttribute> agtAttr;
	
	GamaListReducer listReducer;
	
	public ReferenceList(GamaListReducer l) {
		super(l.getValuesListReducer().size(), l.getContentTypeListReducer());
		agtAttr = new ArrayList<AgentAttribute>();		
		listReducer = l;
	}

	public Object constructReferencedObject(SimulationAgent sim) {

		listReducer.unreferenceReducer(sim);
		return listReducer.constructObject(sim.getScope());	
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
