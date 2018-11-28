package br.ufc.insightlab.ror.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResultQuery {
	private int id;
	private Map<String, String> values;
	
	public ResultQuery(int id){
		this.id = id;
		values = new HashMap<String, String>();
	}
	
	public void addValue(String name,String value){
		values.put(name, value);
	}
	
	public String getValue(String name){
		return values.get(name);
	}

	public Set<String> getProjections(){
		return values.keySet();
	}
	
	public int size(){
		return values.size();
	}
	
	public String toString(){
		String retorno = "ID: "+id+"\n";
		for(String key : values.keySet())
			retorno += "("+key+") "+values.get(key)+"\t";
		return retorno;
	}
	
	
}