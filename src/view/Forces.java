package view;

public class Forces {
	private String _key;
	private String _desc;
	private String _value;
	
	Forces(String key, String desc) {
		_key = key;
		_desc=desc;
		_value=null;
	}
	
	public String getKey() {
		return _key;
	}
	public String getDesc() {
		return _desc;
	}
	
	public String getValue() {
		return _value;
	}
	public void setValue(String value) {
		 _value=value;
	}
}
