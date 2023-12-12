package view;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;


public class ForceLawsTableModel extends AbstractTableModel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	// ...
	private static String[] columnNames = {"Key", "Value", "Description"};
	private List<Forces> list;
	private String type;
	
	
	JSONArray jArray1;
	Controller ctrl;
	ForceLawsTableModel() {		
			list= new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
			
		return list.size();
	}
	@Override
	public int getColumnCount() {
			
		return columnNames.length;
	}
		@Override
	public String getColumnName(int column) {
			
		return columnNames[column];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String s="";
	switch(columnIndex) {
		case 0:
			
			s=list.get(rowIndex).getKey();
			
			break;
			 
		case 1:
			
			s=list.get(rowIndex).getValue();
			break;
				
		case 2:	
			s=list.get(rowIndex).getDesc();
			break;

				
		}
		return s;
	}
	
	public void setValueAt(Object value, int row, int col) {
		
			
			list.get(row).setValue(value.toString());
			

	}
	public void update() {
		
		fireTableDataChanged();;		
	}
	
	public void  setData(JSONObject jsonData, String type) {
		
		this.type=type;
		list=new ArrayList<>();                   //Se resetea la lista 
		Iterator<String> x = jsonData.keys();     //Transforma el jsonObject en una lista
		while (x.hasNext()){
		    String key =  x.next();
		    Forces forces=new Forces(key, jsonData.getString(key));
		    list.add(forces);

		}
		update();
	}
	
	public boolean isCellEditable(int row, int col) {
		return col==1;
	}

	//Transforma la lista en JSON. Solo se convierte a JSON si se ha modificado la variable value.
	public JSONObject getData() throws Exception {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		
		if(list.size()>0) {

			for(int i=0;i<list.size();i++) {
				
				if(list.get(i).getValue()!=null && !list.get(i).getValue().equals("")){
					
					if(list.get(i).getKey()=="c") {  //Al no poderse comprobar si el valor es un double porque hay que meter un vector, comprueba en ese caso en especifico si se cumple
													// que se metan dos valores double.
						
						String theString=list.get(i).getValue();
						String[] splittedString = theString.substring(1, theString.length()-1).split(",") ;
						
						if(splittedString.length!=2) {
							throw new Exception("JSONObject[\""+list.get(i).getKey()+"\"] is not a vector([x,y])\"")
;						}else {
							
							try {
								Double d=Double.parseDouble(splittedString[0]);
								Double d2=Double.parseDouble(splittedString[1]);
								JSONArray jArray = new JSONArray();
								jArray.put(d);
								jArray.put(d2);
	
								data.put(list.get(i).getKey(),jArray);
							}catch(NumberFormatException ex) {
								throw new Exception("JSONObject[\""+list.get(i).getKey()+"\"] is not a number\"");
							}
							
							
						}
						
					}else {
						
						try {
							Double d=Double.parseDouble(list.get(i).getValue());
							data.put(list.get(i).getKey(), d);
							
						}catch(NumberFormatException ex) {

							throw new Exception("JSONObject[\""+list.get(i).getKey()+"\"] is not a number\"");
						}
						
					}
					
					
				}
			}
			
		}
				json.put("type", type);
		        json.put("data", data);
			
				
			
			return json;
	}

}
