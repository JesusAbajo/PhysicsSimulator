package simulator.factories;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	
	public BasicBodyBuilder() {
		
		this.typeTag = "basic";
		this.desc = "Default body";
		
	}
	
	@Override
	protected Body createTheInstance(JSONObject data) throws IllegalArgumentException{
		try {
			String id = data.getString("id");
			JSONArray p = data.getJSONArray("p");
			JSONArray v = data.getJSONArray("v");
			Double m = data.getDouble("m");
			Vector2D pos= new Vector2D(p.getDouble(0),p.getDouble(1));
			Vector2D vel= new Vector2D(v.getDouble(0),v.getDouble(1));
			
			return new Body(id, vel, pos, m);
			
		}catch(JSONException ex) {
			throw new IllegalArgumentException("Error: data not valid", ex);
		}
		
	}
		
	
	protected JSONObject createData() {
		
		JSONObject data = new JSONObject();
		
		data.put("id", "the identifier");
		data.put("m", "the mass");
		data.put("p", "position");
		data.put("v", "velocity");

		 
		return data;
	}

}
