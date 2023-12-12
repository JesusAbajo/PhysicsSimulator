package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;


public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {

	public EpsilonEqualStatesBuilder() {
		this.typeTag = "epseq";
		this.desc = "Epsilon equal states";
	}

	@Override
	protected StateComparator createTheInstance(JSONObject data) throws IllegalArgumentException{
		try {
			Double eps = data.has("eps") ? data.getDouble("eps"):0.0;
			
			
			return new EpsilonEqualStates(eps);
			
		}catch(JSONException ex) {
			
			throw new IllegalArgumentException("Error: data not valid", ex);
		}
		
	}
	
	protected JSONObject createData() {
		
		JSONObject data = new JSONObject();
		
		data.put("eps", "Epsilon");
 
		return data;
	}

}
