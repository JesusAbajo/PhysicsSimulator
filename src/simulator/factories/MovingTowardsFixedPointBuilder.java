package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;


public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	private final double _gravity=9.81;
	public  MovingTowardsFixedPointBuilder() {
		this.typeTag = "mtfp";
		this.desc = "Moving towards a fixed point";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) throws IllegalArgumentException {
		try {
			Double g = data.has("g") ? data.getDouble("g"):_gravity;
			Vector2D point= new Vector2D(0.0,0.0);
			
				if(data.has("c")) {
					JSONArray c = data.getJSONArray("c");
					point =new Vector2D(c.getDouble(0),c.getDouble(1));
				}

			return new MovingTowardsFixedPoint(point, g);
		
		}catch(JSONException ex) {
			throw new IllegalArgumentException("Error: data not valid", ex);
			
		}
	}
	
	protected JSONObject createData() {
		
		JSONObject data = new JSONObject();
		
		data.put("c", "the point towards which bodies move\r\n" + 
				"(a json list of 2 numbers, e.g., [100.0,50.0])");
		data.put("g", "the length of the acceleration vector (a number)");
		
 
		return data;
	}

}
