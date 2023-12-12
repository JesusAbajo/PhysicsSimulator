package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	private List<Builder<T>> builders;
	List<JSONObject> list;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		
		this.builders=new ArrayList<Builder<T>> (builders);
		
		list= new ArrayList<JSONObject>();
		
		for(int i=0;i<builders.size();i++) {
			
			list.add(builders.get(i).getBuilderInfo());
			
		}
	}

	@Override
	public T createInstance(JSONObject info)  {
		int i=0;
		T temp=null;
		
		while(temp==null&&i<builders.size()) {
			
	
				temp=builders.get(i).createInstance(info);
		
			i++;
		}
		
		return temp;
	}

	@Override
	public List<JSONObject> getInfo() {
		return list;
	}

}
