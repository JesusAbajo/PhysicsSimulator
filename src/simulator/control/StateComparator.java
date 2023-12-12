package simulator.control;

import org.json.JSONObject;

public interface StateComparator {
	
	
	boolean equal(JSONObject s1, JSONObject s2);
	
	public JSONObject getOb1();
	public JSONObject getOb2();
}
