package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private JSONObject actual, expected, obj1, obj2;
	private int step;

	
	NotEqualStatesException(JSONObject expected, JSONObject actual, JSONObject obj1, JSONObject obj2, int step){
		
		super("States are different at step " + step+ System.lineSeparator()+
				"Actual: "+ actual + System.lineSeparator()+
				"Expected: "+ expected + System.lineSeparator()+
				"Actual body: "+ obj1+System.lineSeparator()+
				"Expected body: "+ obj2+System.lineSeparator());
		
		this.step=step;
		this.actual=actual;
		this.expected=expected;
		this.obj1=obj1;
		this.obj2=obj2;
		
	}


	public JSONObject getActual() {
		return actual;
	}


	public JSONObject getExpected() {
		return expected;
	}


	public JSONObject getObj1() {
		return obj1;
	}

	public JSONObject getObj2() {
		return obj2;
	}

	public int getStep() {
		return step;
	}


	
}
