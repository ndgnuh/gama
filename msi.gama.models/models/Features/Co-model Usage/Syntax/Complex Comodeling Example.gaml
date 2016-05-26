/**
* Name: comodel_with_the_coupling
* Author: HUYNH Quang Nghi
* Description: This is a simple comodel serve to demonstrate the importation and instatiation of micro-model using the couplings  
* Tags: comodel
*/
model complex_comodeling_example

import "Flies Coupling.gaml" as MyFliesCouplingAliasName
import "Mosquitos Coupling.gaml" as MyMosquitosCouplingAliasName

global
{
	geometry shape<-envelope(square(100));
	init{
		//micro_model must be instantiated by create statement. We create an experiment inside the micro-model and the simulation will be created implicitly (1 experiment have only 1 simulation).
		create MyFliesCouplingAliasName.FliesCouplingExperiment;
		create MyMosquitosCouplingAliasName.MosquitosCouplingExperiment number:5;
	}
	reflex simulate_micro_models{
		
		//tell all experiments of micro_model_1 do 1 step;
		ask (MyFliesCouplingAliasName.FliesCouplingExperiment collect each.simulation){
			do _step_;
		}
		
		//tell the first experiment of micro_model_2 do 1 step;
		ask (MyMosquitosCouplingAliasName.MosquitosCouplingExperiment collect each.simulation){
			do _step_;
		}
	}
}

experiment ComodelingExampleComplex type: gui{
	output{
		display "Comodel Display" {
			//to display the agents of micro-models, we use the agent layer with the values come from the coupling.
			agents "agentB" value:(MyMosquitosCouplingAliasName.MosquitosCouplingExperiment accumulate each.get_mosquitos());
			agents "agentA" value:(MyFliesCouplingAliasName.FliesCouplingExperiment accumulate each.get_flies());
		}
	}
}