package simulator.factories;

import org.json.JSONObject;


import simulator.model.ForceLaws;

import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public  NoForceBuilder() {
		this.typeTag = "nf";
		this.desc = "No force";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) throws IllegalArgumentException{
		
	
		return new NoForce();
	}
	
}
