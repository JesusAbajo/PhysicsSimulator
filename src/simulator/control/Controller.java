package simulator.control;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private PhysicsSimulator ps;
	private Factory<Body> factoryB;
	private Factory<ForceLaws> factoryFl;
	
		public Controller(PhysicsSimulator a, Factory<Body> b, Factory<ForceLaws> c) {
			ps=a;
			factoryB=b;
			factoryFl=c;
			
		}
		
		public void loadBodies(InputStream in) {
			JSONObject jsonInput = new JSONObject(new JSONTokener(in));
			JSONArray array = jsonInput.getJSONArray("bodies");
	
			for(int i = 0; i<array.length();i++) {
				ps.addBody(factoryB.createInstance(array.getJSONObject(i)));
			}
		}
		
		public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException {
			
			JSONObject expOutJson=null;
			
			if(expOut!=null) {
				expOutJson = new JSONObject(new JSONTokener(expOut));
			}
			
			if(out==null) { //este es el cambio que hay que hacer en run?
				out= new OutputStream() {

					@Override
					public void write(int b) throws IOException {
						
						
					}
					
				};
				
			}
			
			PrintStream p = new PrintStream(out);
			p.println("{");
			p.println("\"states\": [");
			
			JSONObject currentState=null;  //estado actual
			JSONObject expState=null;      //estado esperado
			
			currentState= ps.getState();
			p.println(currentState);
			
			if(expOutJson!=null) {
				
				expState=expOutJson.getJSONArray("states").getJSONObject(0);
				
				if(!cmp.equal(expState, currentState)) {
					throw new NotEqualStatesException(expState, currentState,cmp.getOb1(),cmp.getOb2(), 0);
				}
				
			}
			
			for(int i=1; i <= n; i++) {
				ps.advance();
				currentState = ps.getState();
				
				
				p.println(","+currentState);
				
				if(expOutJson!=null) {
					expState=expOutJson.getJSONArray("states").getJSONObject(i);
					
					if(!cmp.equal(expState, currentState)) {
						throw new NotEqualStatesException(expState, currentState, cmp.getOb1(), cmp.getOb2(), i);
					}
				}	
			}
				
			p.println("]");
			p.println("}");
			
		}
		
		public void reset() {
			
			ps.reset();
		}
		
		public void setDeltaTime(double dt) {
			ps.setDeltaTime(dt);
		}
		
		public void addObserver(SimulatorObserver o) {
			
			ps.addObserver(o);
		}
		
		public List<JSONObject>getForceLawsInfo(){
			
			return factoryFl.getInfo();
			
		}
		
		public void setForceLaws(JSONObject info) {
			
			ps.setForceLaws(factoryFl.createInstance(info));
		}
}
