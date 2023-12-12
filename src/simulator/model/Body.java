package simulator.model;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String id;
	protected double m;
	protected Vector2D v, f, p;
	
	public Body(String id, Vector2D velocidad, Vector2D posicion, Double masa) {
		this.id=id;
		v=velocidad;
		p=posicion;
		m=masa;
		f=new Vector2D(0,0);
		
		
	}
	
	public String getId() {
		
		return id;
	}
	
	public Vector2D getVelocity() {
		
		return v;
	}
	
	public Vector2D getForce() {
		return f;
		
	}
	
	public Vector2D getPosition() {
		return p;
		
	}
	
	public double getMass() {
		return m;
		
	}
	
	void addForce(Vector2D fuerza) {
		f=f.plus(fuerza);
	}
	
	void resetForce() {
		f=new Vector2D(0,0);
	}
	
	void move(double t) {
		Vector2D a;
		
		if(m == 0) {
			a=new Vector2D(0,0);
		}
		else {
			
			a=f.scale(1.0 / m);
			
		}
		
		p = p.plus(v.scale(t).plus(a.scale(0.5*t*t)));
		
		v = v.plus(a.scale(t));
	}
	
	public JSONObject getState() {
		JSONObject json = new JSONObject();
		
		json.put("id", getId());
		json.put("m", getMass());
		json.put("p", getPosition().asJSONArray());
		json.put("v", getVelocity().asJSONArray());
		json.put("f", getForce().asJSONArray());
	
		return json;
	
	}
	
	public String toString() {
		
		return getState().toString();
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
