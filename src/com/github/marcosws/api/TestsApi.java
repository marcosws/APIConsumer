package com.github.marcosws.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestsApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("==========================================");
		System.out.println(APIConsumer.get("https://viacep.com.br/ws/05264050/json/"));
		System.out.println("==========================================");
		System.out.println(APIConsumer.get("https://httpbin.org/bearer"));
		System.out.println("==========================================");
		Map<String,String> properties = new HashMap<String,String>();
		String json = "{\"key\":\"chave\",\"value\":\"valor\"}";
		File file = new File("C:\\Projetos\\Arquivos\\Arqjpg.jpg");
		properties.put("content-type", "application/json");
		properties.put("accept", "application/json");
		//properties.put("Content-Type", "multipart/form-data;boundary=*****");
		System.out.println(APIConsumer.post("https://httpbin.org/post", properties, json, file));
		System.out.println("==========================================");

	}

}
