package simulator.factories;
import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;


public class MassEqualStatesBuilder extends Builder<StateComparator> {

	public MassEqualStatesBuilder() {
		this.typeTag = "masseq";
		this.desc = "Mass equality";
	}

	@Override
	protected StateComparator createTheInstance(JSONObject data) throws IllegalArgumentException {
		
	
		return new MassEqualStates();
	}
	
	
}
