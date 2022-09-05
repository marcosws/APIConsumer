package com.github.marcosws.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestsApi2 {

	public static void main(String[] args) {
		
		// https://datausa.io/about/api/
		
		
	/*	System.out.println("===== GET ===============================");
		String retorno = new APIConsumer2()
			.url("https://viacep.com.br/ws/05264050/json/")
			.get()
			.printStatusMessage()
			.getResult();
		System.out.println(retorno); */
		System.out.println("===== POST ==============================");
		String json = "{\"key\":\"chave\",\"value\":\"valor\"}";
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("Content-Type", "application/json; utf-8");
		properties.put("Accept", "application/json");
		String retorno1 = new APIClient()
				.url("https://httpbin.org/post")
				.setRequestProperties(properties)
				.setDoOutput(true)
				.setDoInput(true)
				.post()
				.setText(json)
				.printStatusMessage()
				.getResult();
		System.out.println(retorno1);
		System.out.println("===== POST ==============================");
		File file = new File("C:\\Projetos\\Arquivos\\Arq.bin");
		Map<String,String> propertiesff = new HashMap<String,String>();
		propertiesff.put("Content-Type","multipart/form-data;boundary=*****");
		String retorno2 = new APIClient()
				.url("https://httpbin.org/post")
				.setDoOutput(true)
				.setDoInput(true)
				.post()
				.setRequestProperties(propertiesff)
				.setFile(file)
				.printStatusMessage()
				.getResult();
		System.out.println(retorno2);
		System.out.println("===== POST ==============================");
		Map<String,String> properties2 = new HashMap<String,String>();
		properties2.put("accept", "text/plain; v=1.0");
		properties2.put("Content-Type", " application/json; v=1.0");
		String retorno3 = new APIClient()
		.url("http://fakerestapi.azurewebsites.net/api/v1/Activities")
		.setDoOutput(true)
		.post()
		.setRequestProperties(properties2)
		.setText("{\"id\":2772,\"title\":\"API Testing\",\"dueDate\":\"2022-01-17T14:19:18.900Z\",\"completed\":true}")
		.printStatusMessage()
		.getResult();
		System.out.println(retorno3);
		System.out.println("===== GET ===============================");
		Map<String,String> properties3 = new HashMap<String,String>();
		//properties3.put("accept", "text/plain; v=1.0");
		String retorno = new APIClient()
			.url("http://fakerestapi.azurewebsites.net/api/v1/Activities/1")
			.get()
			.setRequestProperties(properties3)
			.printStatusMessage()
			.getResult();
		System.out.println(retorno);
				
				
	}

}
