package simulator.factories;

import org.json.JSONObject;

public  abstract class  Builder<T> {
	
	protected String typeTag;
	protected String desc;
	
	
	public T createInstance(JSONObject info) {
		T temp = null;
		
		if(typeTag != null && typeTag.equals(info.getString("type"))) {
			
			temp = createTheInstance(info.getJSONObject("data"));
			
		
		}
				
		return temp;
	}
	
	protected abstract T createTheInstance(JSONObject jsonObject) throws IllegalArgumentException;
	

	
	public JSONObject getBuilderInfo() {

        JSONObject info = new JSONObject();
        info.put("type", typeTag);
        info.put("data", createData());
        info.put("desc", desc);
        
        return info;

    }

	protected JSONObject createData() {
		
		return new JSONObject();
	}
}
