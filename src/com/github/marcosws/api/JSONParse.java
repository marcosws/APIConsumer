package com.github.marcosws.api;

import java.util.HashMap;
import java.util.Map;

public class JSONParse {
	
	public Map<String,String> getValues(String key, String json){
		
		Map<String,String> mapJson = new HashMap<String,String>();
		mapJson = getJsonValues(json);
		return getValues(mapJson.get(key));
	}
	
	public Map<String,String> getValues(String json){
		
		String[] arrJson = json.replace("{", "").replace("}", "").split(",");
		Map<String,String> mapJson = new HashMap<String,String>();
		for(String a: arrJson){
			String[] linha = a.split(":");
			mapJson.put(linha[0].replace("\"", "").trim(), new String(linha[1].replace("\"", "").trim()));
		}
		return mapJson;
	}
	
	public Map<String,String> getJsonValues(String json){
	
		Integer coluna = 0;
		String chave = "";
		String conteudoJson = "";
		Map<String,String> mapJson = new HashMap<String,String>();
		for(String j: json.split("},")){
			for(String x: j.replace("[{", "").replace("}}]", "").replace(":{", ";").split(";")){
				if(coluna.equals(0))
					chave = x.replace("\"", "");
				else
					conteudoJson = "{".concat(x).concat("}");
				coluna++;
			}
			mapJson.put(chave, conteudoJson);
			coluna = 0;
		}
		return mapJson;
	}
	
	/**
	 * Recuperar valores
	 * @author Marcos Souza
	 * @param chave
	 * @param json
	 * @return Map<String,String>
	 */
	public Map<String,String> recuperarValores(String chave, String json){
		
		Integer coluna = 0;
		String chaveMap = "";
		String conteudoJson = "";
		Map<String,String> mapJson = new HashMap<String,String>();
		for(String j: json.split("},")){
			for(String x: j.replace("[{", "").replace("}}]", "").replace(":{", ";").split(";")){
				if(coluna.equals(0))
					chaveMap = x.replace("\"", "");
				else
					conteudoJson = "{".concat(x).concat("}");
				coluna++;
			}
			mapJson.put(chaveMap, conteudoJson);
			coluna = 0;
		}
		return recuperarValores(mapJson.get(chave));
		
	}
	
	/**
	 * Recuperar valores
	 * @param json
	 * @return Map<String,String>
	 */
	public Map<String,String> recuperarValores(String json){
		
		Map<String,String> mapJson = new HashMap<String,String>();
		for(String a: json.replace("{", "").replace("}", "").split(",")){
			String[] linha = a.split(":");
			mapJson.put(linha[0].replace("\"", "").trim(), new String(linha[1].replace("\"", "").trim()));
		}
		return mapJson;
	}


}
