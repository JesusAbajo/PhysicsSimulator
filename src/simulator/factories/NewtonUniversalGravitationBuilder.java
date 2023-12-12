package simulator.factories;
import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	private final double G=6.67E-11;
	
	public NewtonUniversalGravitationBuilder() {
		this.typeTag = "nlug";
		this.desc = "Newton’s law of universal gravitation";
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) throws IllegalArgumentException{
		try {
			Double g = data.has("G") ? data.getDouble("G"):G;
			
			return new NewtonUniversalGravitation(g);
			
		}catch(JSONException ex) {
			
		throw new IllegalArgumentException("Error: data not valid", ex);
		}
	}
	
	protected JSONObject createData() {
		
		JSONObject data = new JSONObject();
		
		data.put("G", "the gravitational constant (a number)");
 
		return data;
	}

}
