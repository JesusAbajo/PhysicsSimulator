package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator {

	private JSONObject ob1=null, ob2=null;
	
    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {
        boolean comparator=false;
        int i = 0;

        if(s1.getDouble("time") == (s2.getDouble("time"))){
            
            JSONArray jArray1 =s1.getJSONArray("bodies");
            JSONArray jArray2 =s2.getJSONArray("bodies");
            
            if(jArray1.length()==jArray2.length()) {
            	
            	comparator=true;
            	
            	  while( i < jArray1.length() && comparator == true) {
                  	//compara masas || compara id 
            		  
                      if (jArray1.getJSONObject(i).getDouble("m") != jArray2.getJSONObject(i).getDouble("m") || !jArray1.getJSONObject(i).getString("id").equals(jArray2.getJSONObject(i).getString("id"))) {
                    	  	
                              comparator = false;
                             
                              
                      }

                      i++;
                  }
            }
        }
        
        if(!comparator) {
        	ob1=s2.getJSONArray("bodies").getJSONObject(i-1);
          	ob2=s1.getJSONArray("bodies").getJSONObject(i-1);
        	
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