package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private double dt=0.0, tiempoActual=0.0;
	private List<Body> lista;
	private ForceLaws fuerzas;
	private List<SimulatorObserver> observerList;
	
	
	public PhysicsSimulator(double t, ForceLaws fuerzas) throws  IllegalArgumentException{
		
		if(t<0) {
			 
			throw new IllegalArgumentException("Error: time < 0");
			
		}else if(fuerzas == null) {
			
			throw new IllegalArgumentException("Error: force cannot be null");
		
		}else {
			
			dt=t;
			this.fuerzas=fuerzas;
			this.lista = new ArrayList<Body>();
			this.observerList = new ArrayList<SimulatorObserver>();
		}
	}
	
	public void advance() {
		
		for(int i=0; i<lista.size();i++) {
			
			lista.get(i).resetForce();
			
		}
		fuerzas.apply(lista);
		
		for(int i=0; i<lista.size();i++) {
			
			lista.get(i).move(dt);
			
		}
		tiempoActual+=dt;
		for(SimulatorObserver o: observerList) {
			o.onAdvance(lista, tiempoActual);
		}
		
	}
	
	public void addBody(Body b) throws IllegalArgumentException{
		if(lista.contains(b)) 
			throw new IllegalArgumentException("Error: Bodies have the same id");
		else
			lista.add(b);
		for(SimulatorObserver o: observerList) {
			o.onBodyAdded(lista, b);
		}
		
	}
  
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		
		json.put("time", tiempoActual);
		
		JSONArray jArray = new JSONArray();
		
		for(int i=0; i<lista.size();i++) {
			jArray.put(lista.get(i).getState());
		}
		
		json.put("bodies", jArray);
		
		return json;
		
	}
	
	public String toString() {
		
		return getState().toString();
	}
	
	public void reset() {
		this.lista = new ArrayList<Body>();
		tiempoActual=0.0;
		for(SimulatorObserver o: observerList) {
			o.onReset(lista, tiempoActual, dt, fuerzas.toString());
		}
	}
	
	public void setDeltaTime(double dt) {
			
			if(dt<0)
				
				throw new IllegalArgumentException("Error: delta time < 0");
				
			else {
				this.dt=dt;
				for(SimulatorObserver o: observerList) {
					o.onDeltaTimeChanged(this.dt);
				}
			}
				
				
		
	}
	
	public void setForceLaws(ForceLaws forceLaws) {
		if(forceLaws==null)
			throw new IllegalArgumentException("Error: force cannot be null (setForceLawsLaws)");
		else {
			fuerzas=forceLaws;
			for(SimulatorObserver o: observerList) {
				o.onForceLawsChanged(fuerzas.toString());
			}
		}
		
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!observerList.contains(o)) {
			observerList.add(o);
			o.onRegister(lista, tiempoActual, dt, fuerzas.toString());
		
		}
	
	}
}
