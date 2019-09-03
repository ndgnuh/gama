package gama.extensions.serialize.gamaType.reference;

import java.util.ArrayList;

import gama.extensions.serialize.gamaType.reduced.GamaGraphReducer;
import gama.common.interfaces.IReference;
import gama.common.interfaces.IReference.AgentAttribute;
import gama.kernel.simulation.SimulationAgent;
import gama.util.graph.GamaGraph;
import gaml.types.Types;

public class ReferenceGraph extends GamaGraph implements IReference {
//	IAgent agt;
//	String attributeName;
	ArrayList<AgentAttribute> agtAttr;
	
	GamaGraphReducer graphReducer;

	public ReferenceGraph(GamaGraphReducer g) {
		super(null, Types.NO_TYPE, Types.NO_TYPE);
		agtAttr = new ArrayList<AgentAttribute>();		
		graphReducer = g;
	}	
	
//	public IAgent getAgt() {return agt;}
//	public String getAttributeName() {return attributeName;}
		
//	public void setAgentAndAttrName(IAgent _agt, String attrName) {
//		agt = _agt;
//		attributeName = attrName;
//	}

	@Override
	public Object constructReferencedObject(SimulationAgent sim) {

	//	graphReducer.setValuesGraphReducer((GamaMap)IReference.getObjectWithoutReference(graphReducer.getValuesGraphReducer(),sim));
	//	graphReducer.setEdgesWeightsGraphReducer((GamaMap)IReference.getObjectWithoutReference(graphReducer.getWeightsGraphReducer(),sim));
		
	//	Map<Object,Object> mapWithReferences = mapReducer.getValues();
	//	HashMap<Object,Object> mapWithoutReferences = new HashMap<>();
		
	//	for(Map.Entry<Object,Object> e : mapWithReferences.entrySet()) {
	//		mapWithoutReferences.put(
	//				IReference.getObjectWithoutReference(e.getKey(),sim), 
	//				IReference.getObjectWithoutReference(e.getValue(),sim));
	//	}
		
	//	mapReducer.setValues(mapWithoutReferences);

		graphReducer.unreferenceReducer(sim);
		return graphReducer.constructObject(sim.getScope());
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
