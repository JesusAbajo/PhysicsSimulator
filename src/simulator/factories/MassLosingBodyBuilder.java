package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder() {
		this.typeTag = "mlb";
		this.desc = "Mass losing body";
	}

	@Override
	protected Body createTheInstance(JSONObject data) throws IllegalArgumentException {
		try {
			
		String id = data.getString("id");
		JSONArray p = data.getJSONArray("p");
		JSONArray v = data.getJSONArray("v");
		Vector2D pos= new Vector2D(p.getDouble(0),p.getDouble(1));
		Vector2D vel= new Vector2D(v.getDouble(0),v.getDouble(1));
		Double m = data.getDouble("m");
		Double freq = data.getDouble("freq");
		Double factor = data.getDouble("factor");
		
		
		return new MassLossingBody(id, vel, pos, m, factor, freq);
		
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
		data.put("freq", "frequency");
		data.put("factor", "lost percentage");

		 
		return data;
	}

}
