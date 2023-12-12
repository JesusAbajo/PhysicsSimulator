package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
    private double _G=6.67E-11;
 
    public NewtonUniversalGravitation(double g) {
    	this._G=g;
    }
    
    
    @Override
    public void apply(List<Body> bs) {
        
        
        for(int x=0; x<bs.size();x++) {
 	
                for(int y=0; y<bs.size();y++) {
                	
                		if(x!=y) 
                			bs.get(x).addForce(force(bs.get(x), bs.get(y)));
	 
                } 	    
        }

    }

    private Vector2D force(Body a, Body b) {
        Vector2D delta = b.getPosition().minus(a.getPosition());
        double dist = delta.magnitude();
        double magnitude = dist>0 ? (_G* a.getMass() * b.getMass()) / (dist * dist) : 0.0;
        return delta.direction().scale(magnitude);
    }
    
    public String toString() {
    	
    	
		return "Newton’s Universal Gravitation with G= "+_G;
    }
}