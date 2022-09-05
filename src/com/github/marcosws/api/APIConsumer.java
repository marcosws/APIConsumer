package com.github.marcosws.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

@Deprecated
public class APIConsumer {
	
	private final static int TAM_MAX_BUFFER = 10240; // 10Kbytes
	private final static String GET = "GET";
	private final static String POST = "POST";
	
	
	public static String get(String strUrl){
		return get(strUrl, null);
	}
	
	public static String get(String strUrl, Map<String,String> requestProperties){
		
		try {
			
			URL url = new URL(strUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setRequestMethod(GET);
			if(requestProperties != null) requestProperties.forEach((n,v) -> { httpUrlConnection.setRequestProperty(n,v); });
			printStatusMessage(httpUrlConnection);
			return getResult(httpUrlConnection);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}
	
	public static String post(String strUrl, Map<String,String> requestProperties, String json, File file){
		
	    String crlf = "\r\n";
	    String twoHyphens = "--";
	    String boundary =  "*****";
		
		try{
			
			URL url = new URL(strUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			requestProperties.forEach((n,v) -> { httpUrlConnection.setRequestProperty(n,v); });
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
			httpUrlConnection.setRequestMethod(POST);
			
            OutputStream os = httpUrlConnection.getOutputStream();
            os.write(json.getBytes("UTF-8")); 
            os.close();
            
    	/*    DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream()); 
    	    request.writeBytes(twoHyphens + boundary + crlf);
    	    request.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + file.getAbsolutePath() + "\"" + crlf);
    	    request.writeBytes(crlf);
    	    request.write(Files.readAllBytes(file.toPath()));
    	    request.writeBytes(crlf);
    	    request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
    	    request.flush();
    	    request.close();  */
            
            printStatusMessage(httpUrlConnection);
            return getResult(httpUrlConnection);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";

		
	}
	
	
	private static String getResult(HttpURLConnection httpUrlConnection) {
		
		try {
			if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
				InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());    
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), TAM_MAX_BUFFER);
			    StringBuilder builder = new StringBuilder();
			    for(String line = null; (line = reader.readLine())!= null;)
			    	builder.append(line).append("\n");
			    return builder.toString();
			}

		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	private static void printStatusMessage(HttpURLConnection httpUrlConnection) {
		
		try {
			System.out.print("Method: ");
			Message.message("[" + httpUrlConnection.getRequestMethod() + "]");
			if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
				System.out.print("Status: ");
				Message.messageOK("[" + httpUrlConnection.getResponseCode() + "] - " + httpUrlConnection.getResponseMessage());
			}
			else {
				System.out.print("Status: ");
				Message.messageNOK("[" + httpUrlConnection.getResponseCode() + "] - " + httpUrlConnection.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
