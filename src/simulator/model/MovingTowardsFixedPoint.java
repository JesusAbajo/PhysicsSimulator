package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	private double g=9.81;
	Vector2D origen=new Vector2D(0,0);


	public MovingTowardsFixedPoint(Vector2D point, Double g) {
		origen=point;
		this.g=g;
	}



	@Override
	public void apply(List<Body> bs) {
		Vector2D fuerza;
		
		 for(int x=0; x<bs.size();x++) {
			 
			 fuerza=origen.minus(bs.get(x).getPosition()).direction().scale(g*bs.get(x).getMass()); // a*m  a.scale(m)
			
			 bs.get(x).addForce(fuerza);
		 }
	}
	
	 public String toString() {
	    	
	    	
			return "Moving towards "+origen+" with constant acceleration "+g;
	    }
}
