package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	private JSONObject ob1=null, ob2=null;
    private double eps;

    public EpsilonEqualStates(double eps) {

        this.eps = eps;

    }


    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {
           boolean comparator=false;


            if(s1.getDouble("time") == (s2.getDouble("time"))){
                int i = 0;
                JSONArray jArray1 =s1.getJSONArray("bodies");
                JSONArray jArray2 =s2.getJSONArray("bodies");
                
                if(jArray1.length()==jArray2.length()) {
                	comparator = true;
                	
                	  while( i < jArray1.length() && comparator == true) {
                		  
                      	Vector2D v1 =  new Vector2D (jArray1.getJSONObject(i).getJSONArray("p").getDouble(0), jArray1.getJSONObject(i).getJSONArray("p").getDouble(1));
                      	Vector2D v2 =  new Vector2D (jArray2.getJSONObject(i).getJSONArray("p").getDouble(0), jArray2.getJSONObject(i).getJSONArray("p").getDouble(1));  
                      	
                      	
                      	
                          if (!jArray1.getJSONObject(i).getString("id").equals(jArray2.getJSONObject(i).getString("id"))
                              || !(Math.abs(jArray1.getJSONObject(i).getDouble("m")-jArray2.getJSONObject(i).getDouble("m")) <= eps)
                              || !((v1).distanceTo(v2) <= eps)) {
                        	  
                                 comparator = false;
                          }
                          
                          v1 =  new Vector2D (jArray1.getJSONObject(i).getJSONArray("v").getDouble(0), jArray1.getJSONObject(i).getJSONArray("v").getDouble(1));
                          v2 =  new Vector2D (jArray2.getJSONObject(i).getJSONArray("v").getDouble(0), jArray2.getJSONObject(i).getJSONArray("v").getDouble(1));
                          
                          if(!((v1).distanceTo(v2) <= eps)) comparator=false;
                      
                          v1 =  new Vector2D (jArray1.getJSONObject(i).getJSONArray("f").getDouble(0), jArray1.getJSONObject(i).getJSONArray("f").getDouble(1));
                          v2 =  new Vector2D (jArray2.getJSONObject(i).getJSONArray("f").getDouble(0), jArray2.getJSONObject(i).getJSONArray("f").getDouble(1));
                          
                          if(!((v1).distanceTo(v2) <= eps)) comparator=false;


                          i++;
                      }
                	  
                	  if(!comparator) {
                      	ob1=s2.getJSONArray("bodies").getJSONObject(i-1);
                      	ob2=s1.getJSONArray("bodies").getJSONObject(i-1);
                      	
                      }
                }
               
                	
                

            }
 
          
            
            return comparator;
        }


	public JSONObject getOb1() {
		// TODO Auto-generated method stub
		return ob1;
	}

	public JSONObject getOb2() {
		// TODO Auto-generated method stub
		return ob2;

	}
}